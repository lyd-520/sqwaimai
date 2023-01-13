<template>
  <div class="order_page">
    <head-top head-title="订单列表"></head-top>

    <nav class="order_search_nav">
    		<div class="swiper-container">
		        <div class="swiper-wrapper">
		            <div class="swiper-slide order_types_container">
                  <div class="link_to_order" @click="queryOrder(-1)">
                    <figure>
                      <figcaption>全部订单</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order" @click="queryOrder(0)">
                    <figure>
                      <figcaption>待付款</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order" @click="queryOrder(1)">
                    <figure>
                      <figcaption>待配送</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order" @click="queryOrder(2)">
                    <figure>
                      <figcaption>派送中</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order" @click="queryOrder(3)">
                    <figure>
                      <figcaption>待接收</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order"  @click="queryOrder(4)">
                    <figure>
                      <figcaption>待确认</figcaption>
                    </figure>
                  </div>
                  <div class="link_to_order"  @click="queryOrder(5)">
                    <figure>
                      <figcaption>已完成</figcaption>
                    </figure>
                  </div>
		            </div>
		        </div>
		    </div>
    	</nav>
    <!-- <ul class="order_list_ul" v-load-more="loaderMore"> -->
    <ul class="order_list_ul">
      <li class="order_list_li" v-for="item in orderList" :key="item.id">
        <img :src="imgBaseUrl + item.restaurant_image_url" class="restaurant_image">
        <section class="order_item_right">
          <section @click="showDetail(item)">
            <header class="order_item_right_header">
              <section class="order_header">
                <h4>
                  <span class="ellipsis">{{item.restaurant_name}} </span>
                  <svg fill="#333" class="arrow_right">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                  </svg>
                </h4>
                <p class="order_time">{{item.formatted_create_at}}</p>
              </section>
              <p class="order_status">
                {{item.status_title}}
              </p>
            </header>
            <section class="order_basket">
              <p class="order_name ellipsis">{{item.basket.items[0].name}}{{item.basket.items.length > 1 ? ' 等' + item.basket.items.length + '件商品' : ''}}</p>
              <p class="order_amount">¥{{item.total_amount.toFixed(2)}}</p>
            </section>
          </section>
          <div class="order_again">
            <!-- 等待支付 倒计时-->
            <compute-time v-if="item.status_code == '0'" :time="item.time_pass"></compute-time>
            <!-- 订单付款后，可以取消  -->
            <span v-if="item.status_code == '1'" @click="handleCancelOrder(item.id)" tag="span" class="finish">取消订单</span>
            <!-- 订单派送中，可以完成-->
            <span v-if="item.status_code == '4'" @click="handleFinishOrder(item.id)" tag="span" class="finish">订单完成</span>
            <!-- 订单派送完成，可以再来一单 -->
            <router-link v-if="item.status_code == '5'" :to="{path: '/shop', query: {geohash, id: item.restaurant_id}}" tag="span" class="buy_again">再来一单</router-link>
          </div>
        </section>
      </li>
      <li>
          <p>没有更多订单</p>
        </li>
    </ul>
    <foot-guide></foot-guide>
    <transition name="loading">
      <loading v-show="showLoading"></loading>
    </transition>
    <transition name="router-slid" mode="out-in">
      <router-view></router-view>
    </transition>

  </div>
</template>

<script>
  import {mapState, mapMutations} from 'vuex'
  import headTop from 'src/components/header/head'
  import computeTime from 'src/components/common/computeTime'
  import loading from 'src/components/common/loading'
  import footGuide from 'src/components/footer/footGuide'
  import {getOrderList,finishOrder,cancelOrder} from 'src/service/getData'
  import {loadMore} from 'src/components/common/mixin'
  import {imgBaseUrl} from 'src/config/env'


  export default {
    data() {
      return {
        orderList: null, //订单列表
        offset: 0,
        preventRepeat: false,  //防止重复获取
        showLoading: true, //显示加载动画
        imgBaseUrl
      }
    },
    mounted() {
      this.initData();
    },
    mixins: [loadMore],
    components: {
      headTop,
      footGuide,
      loading,
      computeTime,
    },
    computed: {
      ...mapState([
        'userInfo', 'geohash'
      ]),
    },
    methods: {
      ...mapMutations([
        'SAVE_ORDER'
      ]),
      //初始化获取信息
      async initData() {
        if (this.userInfo && this.userInfo.user_id) {
          let response = await getOrderList(this.userInfo.user_id, this.offset,-1);
          let res = response.records
          this.orderList = [...res];
          this.hideLoading();
        } else {
          this.hideLoading();
        }
      },
      async queryOrder(orderStatus){
        this.showLoading=true
        let response = await getOrderList(this.userInfo.user_id, this.offset,orderStatus);
        let res = response.records
        this.orderList = [...res];
        this.hideLoading();
      },
      //加载更多
      async loaderMore() {
        if (this.preventRepeat) {
          return
        }
        this.preventRepeat = true;
        this.showLoading = true;
        this.offset += 10;
        //获取信息
        let res = await getOrderList(this.userInfo.user_id, this.offset,-1);
        this.orderList = [...this.orderList, ...res];
        this.hideLoading();
        if (res.length < 10) {
          return
        }
        this.preventRepeat = false;
      },
      //显示详情页
      showDetail(item) {
        this.SAVE_ORDER(item);
        this.preventRepeat = false;
        this.$router.push('/order/orderDetail');
      },
      //生产环境与发布环境隐藏loading方式不同
      hideLoading() {
        this.showLoading = false;
      },
      handleFinishOrder(orderId) {
        finishOrder(this.userInfo.user_id,orderId).then(res => {
         // for(var i in this.orderList){
         //   const order = this.orderList[i]
         //   if(orderId == order.id){
         //     this.orderList[i] = res
         //   }
         // }
          this.initData()
        })
      },
      handleCancelOrder(orderId){
        cancelOrder(this.userInfo.user_id,orderId).then(res => {
          // for(const i in this.orderList){
          //   const order = this.orderList[i]
          //   if(orderId == order.id){
          //     // order.status_title='已取消'
          //     // order.status_code=-1
          //     this.orderList[i] = res
          //   }
          // }
            this.initData()
        })
      }
    },
    watch: {
      userInfo: function (value) {
        if (value && value.user_id && !this.orderList) {
          this.initData();
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import 'src/style/mixin';


  .order_search_nav{
		padding-top: 2.1rem;
		background-color: #fff;
		border-bottom: 0.025rem solid $bc;
		height: 4rem;
		.swiper-container{
			@include wh(100%, auto);
			padding-bottom: 0.6rem;
			.swiper-pagination{
				bottom: 0.2rem;
			}
		}
		.fl_back{
			@include wh(100%, 100%);
		}
	}

  .order_types_container{
		display:flex;
		flex-wrap: nowrap;
		.link_to_order{
			width: 20%;
			padding: 0.3rem 0rem;
			@include fj(center);
			figure{
				figcaption{
					text-align: center;
          background: lightgray;
					@include sc(0.55rem, #666);
				}
			}
		}
	}

  .order_page {
    background-color: #f1f1f1;
    margin-bottom: 1.95rem;

    p, span, h4 {
      font-family: Helvetica Neue, Tahoma, Arial;
    }
  }

  .order_list_ul {
    padding-top: 0.3rem;

    .order_list_li {
      background-color: #fff;
      display: flex;
      margin-bottom: 0.5rem;
      padding: .6rem .6rem 0;

      .restaurant_image {
        @include wh(2rem, 2rem);
        margin-right: 0.4rem;
      }

      .order_item_right {
        flex: 5;

        .order_item_right_header {
          border-bottom: 0.025rem solid #f5f5f5;
          padding-bottom: .3rem;
          @include fj;

          .order_header {
            h4 {
              display: flex;
              align-items: center;
              justify-content: flex-start;
              @include sc(.75rem, #333);
              line-height: 1rem;
              width: 9rem;

              .arrow_right {
                @include wh(.4rem, .4rem);
                fill: #ccc;
                margin-right: .2rem;
              }
            }

            .order_time {
              @include sc(.55rem, #999);
              line-height: .8rem;
            }
          }

          .order_status {
            @include sc(.6rem, #333);
          }
        }

        .order_basket {
          @include fj;
          line-height: 2rem;
          border-bottom: 0.025rem solid #f5f5f5;

          .order_name {
            @include sc(.6rem, #666);
            width: 10rem;
          }

          .order_amount {
            @include sc(.6rem, #f60);
            font-weight: bold;
          }
        }

        .order_again {
          text-align: right;
          line-height: 1.6rem;

          .buy_again {
            @include sc(.55rem, #3190e8);
            border: 0.025rem solid #3190e8;
            padding: .1rem .2rem;
            border-radius: .15rem;
          }
          .finish {
            @include sc(.55rem, #e8260f);
            border: 0.025rem solid #e8260f;
            padding: .1rem .2rem;
            border-radius: .15rem;
          }
        }
      }
    }
  }

  .loading-enter-active, .loading-leave-active {
    transition: opacity .7s
  }

  .loading-enter, .loading-leave-active {
    opacity: 0
  }

  .router-slid-enter-active, .router-slid-leave-active {
    transition: all .4s;
  }

  .router-slid-enter, .router-slid-leave-active {
    transform: translate3d(2rem, 0, 0);
    opacity: 0;
  }
</style>
