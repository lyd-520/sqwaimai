<template>
  <div class="order_page">
    <head-top head-title="推荐附近订单" go-back='true'>
      <div slot="changecity" class="refresh_order" @click="refreshOrder">刷新</div>
    </head-top>

    <ul class="order_list_ul">
      <span v-if="errormessage">{{errormessage}}</span>
      <li class="order_list_li" v-for="item in orderList" :key="item.id">
        <img :src="imgBaseUrl + item.restaurant_image_url" class="restaurant_image">
        <section class="order_item_right">
          <section>
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
              <p class="order_name ellipsis">商店地址：xx街道xx社区xx号店铺门口</p><br />
            </section>
            <section class="order_basket">
              <p class="order_name ellipsis">店铺距你</p> <p class="order_amount">{{item.total_amount.toFixed(2)}}米</p>
            </section>
          </section>
          <div class="order_again">
            <span tag="span" class="finish" @click="gocheckOrder(item)">抢单</span>
            <span tag="span" class="finish" @click="goshowDetail(item)">查看</span> 
          </div>
        </section>
      </li>
    </ul>
    <alert-tip v-if="showAlert" :showHide="showAlert" @closeTip="closeTip" :alertText="alertText"></alert-tip>
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
  import alertTip from 'src/components/common/alertTip'
  import {getImgPath} from 'src/components/common/mixin'
  import {getOrderList,checkOrder} from 'src/service/getData'
  import {imgBaseUrl} from 'src/config/env'

  export default {
    data() {
      return {
        orderList: null, //订单列表
        showLoading: true, //显示加载动画
        imgBaseUrl,
        errormessage:'',
        showAlert: false, //显示提示组件
        alertText: null, //提示的内容
      }
    },
    mounted() {
      this.initData();
    },
    components: {
      headTop,
      loading,
      alertTip,
      computeTime,
    },
    computed: {
      //获取city页面选择的纬度和经度
      ...mapState([
        'userInfo','latitude','longitude'
      ]),
    },
    methods: {
      ...mapMutations([
        'SAVE_ORDER','RECORD_USERINFO'
      ]),
      //初始化获取信息
      async initData() {
        if (this.userInfo && this.userInfo.rider_id) {
          let response = await getOrderList(this.userInfo.rider_id,this.latitude,this.longitude)
          if(response.records){
            let res = response.records
            this.orderList = [...res];
            this.errormessage=""
          }else{
            this.orderList = [];
            this.errormessage=response
          }
        }
        this.hideLoading();
      },
      //显示详情页
      goshowDetail(item) {
        this.SAVE_ORDER(item);
        this.$router.push('/orderDetail');
      },
      async gocheckOrder(item) {
        if(this.userInfo && this.userInfo.sending_order_id>0){
            this.showAlert=true
            this.alertText='骑手一次只能派送一个订单'
        }else{
          let res = await checkOrder(this.userInfo.rider_id,item.id)
          if(res.error){
            this.showAlert=true
            this.alertText=res.error
          }else{
            //返回用户信息
            this.RECORD_USERINFO(res)
            // this.$router.push('/')
            this.SAVE_ORDER(item);
            this.$router.push('/orderDetail');
        }
      }
    },
      //生产环境与发布环境隐藏loading方式不同
      hideLoading() {
        this.showLoading = false;
      },
      async refreshOrder(){
        this.showLoading = true;
        this.initData();
      },
       closeTip(){
            this.showAlert = false;
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
  .refresh_order {
        right: 0.4rem;
        @include sc(0.6rem, #fff);
        @include ct;
      }
  .order_page {
    background-color: #f1f1f1;
    margin-bottom: 1.95rem;

    p, span, h4 {
      font-family: Helvetica Neue, Tahoma, Arial;
    }
  }

  .order_list_ul {
    padding-top: 1.95rem;

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
