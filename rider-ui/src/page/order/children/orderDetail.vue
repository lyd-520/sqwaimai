 <template>
  <div class="order_detail_page">
        <head-top head-title="订单详情" go-back='false'></head-top>
        <section v-if="(!showLoading && this.orderData.id)" id="scroll_section" class="scroll_container">
            <section class="scroll_insert">
                <section class="order_titel">
                    <img :src="imgBaseUrl+orderData.restaurant_image_url">
                    <p>订单ID：{{orderData.id}}</p>
                    <p>{{orderData.status_title}}</p>
                </section>
                <section class="food_list">
                    <div class="food_list_header">
                        <div class="shop_name">
                            <img :src="imgBaseUrl+orderData.restaurant_image_url">
                            <span>{{orderData.restaurant_name}}</span>
                        </div>
                        <svg fill="#333" class="arrow_right">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                        </svg>
                    </div>
                    <ul class="food_list_ul">
                        <li v-for="item in orderData.basket.group[0]">
                            <p class="food_name ellipsis">{{item.name}}</p>
                            <div class="quantity_price">
                                <span>X{{item.quantity}}</span>
                            </div>
                        </li>
                    </ul>
                    <div class="deliver_fee">
                        <span>配送费</span>
                        <span>{{orderData.basket.deliver_fee&&orderData.basket.deliver_fee.price || 0}}</span>   
                    </div>
                </section>
                <section class="order_detail_style">
                    <header>配送信息</header>
                    <section class="item_style">
                        <p class="item_left">收件人：</p>
                        <div class="item_right">
                            <p>{{orderData.order_address.name}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">电话号码：</p>
                        <div class="item_right">
                            <p>{{orderData.order_address.phone}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">备用电话：</p>
                        <div class="item_right">
                            <p>{{orderData.order_address.phone}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">收件地址：</p>
                        <div class="item_right">
                            <p>{{orderData.order_address.address}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">送达时间：</p>
                        <div class="item_right">
                            <p>{{orderData.formatted_deliver_at}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">地图导航：</p>
                        <div class="item_right">
                            <p>暂不支持</p>
                        </div>
                    </section>
                </section>
                <section class="order_detail_style">
                    <header>订单信息</header>
                    <section class="item_style">
                        <p class="item_left">订单号：</p>
                        <div class="item_right">
                            <p>{{orderData.id}}</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">支付方式：</p>
                        <div class="item_right">
                            <p>在线支付</p>
                        </div>
                    </section>
                    <section class="item_style">
                        <p class="item_left">下单时间：</p>
                        <div class="item_right">
                            <p>{{orderData.formatted_create_at}}</p>
                        </div>
                    </section>
                </section>
                <section class="order_titel">
                    <a class="order_again" v-if="canCheck" @click="gocheckOrder">抢单</a>
                    <a class="order_again" v-if="canSend" @click="confirmsendOrder">订单送达</a>
                </section>
            </section>
        </section>
        <alert-tip v-if="showAlert" :showHide="showAlert" @closeTip="closeTip" :alertText="alertText"></alert-tip>
        <confirm-tip v-if="showConfirm" :showHide="showConfirm"  @reject="rejectsendorder" @confirm="gosendorder" :confirmText="confirmText"></confirm-tip>
        <transition name="loading">
            <loading v-if="showLoading"></loading>
        </transition>
        <foot-guide></foot-guide>
    </div>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import footGuide from 'src/components/footer/footGuide'
    import headTop from 'src/components/header/head'
    import {getImgPath} from 'src/components/common/mixin'
    import {getOrderDetail,checkOrder,sendOrder} from 'src/service/getData'
    import loading from 'src/components/common/loading'
    import alertTip from 'src/components/common/alertTip'
    import confirmTip from 'src/components/common/confirmTip'
    import BScroll from 'better-scroll'
    import {imgBaseUrl} from 'src/config/env'
    

    export default {

      data(){
            return{
                showLoading: true, //显示加载动画
                orderData: null,
                imgBaseUrl,
                canCheck: true ,  //是否可以抢单
                canSend: false , //是否可以送单
                showAlert: false,
                alertText: "",
                showConfirm: false,
                confirmText: ""
            }
        },
        mounted(){
            this.initData();
        },
        mixins: [getImgPath],
        components: {
            headTop,
            loading,
            alertTip,
            confirmTip,
            footGuide
        },
        computed: {
            ...mapState([
                'geohash', 'userInfo','orderDetail'
            ]),
        },
        methods: {
            ...mapMutations([
                'CLEAR_ORDER','RECORD_USERINFO'
            ]),
            async initData(){
                //store中没有userInfo，登录状态有问题。
                if(!this.userInfo || !this.userInfo.rider_id){
                    this.showAlert=true
                    this.alertText="需要登录才能访问"
                    return false
                }
                //store中有orderDetail，就表示是查看订单详情。
                if(this.orderDetail){
                    this.orderData = this.orderDetail
                    if(this.userInfo.sending_order_id<=0){
                        this.canSend=false
                        this.canCheck=false
                    }
                    if(this.userInfo.sending_order_id == this.orderDetail.id){
                        this.canSend=true
                        this.canCheck=false
                    }else{
                        this.canSend = false;
                        if(this.userInfo.sending_order_id <=0){
                            this.canCheck=true
                        }else{
                            this.canCheck=false;
                        }
                    }
                    //store中没有orderDetail，就表示是派单页面，根据用户ID查他派送中的订单。
                }else{
                    if(this.userInfo.sending_order_id<=0){
                        this.showAlert=true
                        this.alertText="当前没有派送中的订单"
                        return false
                    }
                    this.orderData = await getOrderDetail(this.userInfo.sending_order_id)
                    this.canCheck=false
                    this.canSend=true
                }
                this.CLEAR_ORDER()
                this.showLoading = false;       
                this.$nextTick(() => {
                        new BScroll('#scroll_section', {  
                            deceleration: 0.001,
                            bounce: true,
                            swipeTime: 1800,
                            click: true,
                        }); 
                    })          
            },
            closeTip(){
                this.showAlert = false;
                this.$router.push("/")
            },
            async gocheckOrder(){
                if(this.userInfo && this.userInfo.sending_order_id>0){
                    this.showAlert=true
                    this.alertText='骑手一次只能派送一个订单'
                }else{
                    let res = await checkOrder(this.userInfo.rider_id,this.orderData.id)
                    if(res.error){
                        this.showAlert=true
                        this.alertText=res.error
                    }else{
                        //返回用户信息
                        this.RECORD_USERINFO(res)
                        // this.SAVE_ORDER(this.orderData);
                        this.initData();
                    }
                }
            },
            confirmsendOrder(){
                this.showConfirm=true
                this.confirmText="请确认订单已经送达给客户"
            },
            async gosendorder(){
                let res = await sendOrder(this.userInfo.rider_id,this.orderData.id)
    console.info(res)
                if(res.error){
                    this.confirmText=""
                    this.showConfirm=false
                    this.alertText=res.error
                    this.showAlert=true
                }else{
                    this.RECORD_USERINFO(res)
                    this.$router.push("/")
                }
            },
            rejectsendorder(){
                this.showConfirm=false;
                this.confirmText=""
            }
        },
        watch: {
            userInfo: function (value) {
                if (value && value.user_id) {
                    this.initData();
                }
            }
        }
    }
</script>
  
<style lang="scss" scoped>
    @import 'src/style/mixin';
  
    .order_detail_page{
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #f1f1f1;
        z-index: 202;
        padding-top: 1.95rem;
        p, span{
            font-family: Helvetica Neue,Tahoma,Arial;
        }
    }
    .scroll_container{
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        padding-top: 4rem;
    }
    .scroll_insert{
        padding-bottom: 3rem;
    }
    .order_titel{
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: .7rem;
        background-color: #fff;
        margin-bottom: 0.5rem;
        img{
            border: 0.05rem solid $blue;
            border-radius: 50%;
            @include wh(3rem, 3rem);
        }
        p:nth-of-type(1){
            @include sc(.9rem, #333);
            font-weight: bold;
            margin-top: .4rem;
        }
        p:nth-of-type(2){
            @include sc(.55rem, #999);
            width: 10rem;
            margin-top: .3rem;
            text-align: center;
        }
        .order_again{
            @include sc(1rem, $blue);
            margin-top: .5rem;
            border: 0.1rem solid $blue;
            padding: .15rem .4rem;
            border-radius: .1rem;
        }
    }
    .food_list{
        background-color: #fff;
        .food_list_header{
            @include fj;
            align-items: center;
            padding: .2rem .5rem;
            border-bottom: 1px solid #f5f5f5;
            .shop_name{
                img{
                    @include wh(1.2rem, 1.2rem);
                    vertical-align: middle;
                    margin-right: .2rem;
                }
                span{
                    @include sc(.75rem, #333);
                    font-weight: bold;
                }
            }
            svg{
                @include wh(.6rem, .6rem);
                fill: #666;
            }
        }
        .food_list_ul{
            li{
                @include fj;
                align-items: center;
                padding: 0 .5rem;
                line-height: 2rem;
                .food_name{
                    @include sc(.6rem, #666);
                    flex: 4;
                }
                .quantity_price{
                    flex: 1;
                    @include fj;
                    align-items: center;
                    span:nth-of-type(1){
                        @include sc(.6rem, #ccc);
                    }
                    span:nth-of-type(2){
                        @include sc(.6rem, #666);
                    }
                }
            }
        }
        .deliver_fee{
            @include fj;
            align-items: center;
            padding: 0 .5rem;
            line-height: 2rem;
            border-top: 1px solid #f5f5f5;
            span{
                @include sc(.6rem, #666);
            }
        }
        .pay_ment{
            @include sc(.6rem, #fb6b23);
            border-top: 1px solid #f5f5f5;
            font-weight: bold;
            line-height: 2rem;
            text-align: right;
            padding-right: .5rem;
        }
    }
    .order_detail_style{
        background-color: #fff;
        margin-top: 0.5rem;
        header{
            @include sc(.75rem, #333);
            padding: .5rem;
            border-bottom: 1px solid #f5f5f5;
        }
        .item_style{
            display: flex;
            padding: .5rem;
            p{
                @include sc(.65rem, #666);
                line-height: 1rem;
            }
        }
    }
    .loading-enter-active, .loading-leave-active {
        transition: opacity .7s
    }
    .loading-enter, .loading-leave-active {
        opacity: 0
    }
    
</style>
