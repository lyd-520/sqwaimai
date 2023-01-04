<template>
    <div class="profile_page">
        <head-top go-back='true' :head-title="profiletitle"></head-top>
        <section>
            <section class="profile-number">
                <router-link :to="userInfo&&userInfo.rider_id? '/profile/info' : '/login'" class="profile-link">
                    <img :src="imgBaseUrl + userInfo.avatar" class="privateImage" v-if="userInfo&&userInfo.rider_id">
                    <span class="privateImage" v-else>
                        <svg class="privateImage-svg">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#avatar-default"></use>
                        </svg>
                    </span>
                    <div class="user-info">
                        <p>{{username}}</p>
                        <p>
                            <span class="user-icon">
                                <svg class="icon-mobile" fill="#fff">
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#mobile"></use>
                                </svg>
                            </span>
                            <span class="icon-mobile-number">{{mobile}}</span>
                        </p>
                        <p><span class="icon-mobile-number">服务城市：{{guessCity}}</span></p>
                    </div>
                    <span class="arrow">
                        <svg class="arrow-svg" fill="#fff">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                        </svg>
                    </span>
                </router-link>
            </section>
            <section class="info-data">
                <ul class="clear">
                    <router-link to="/balance" tag="li" class="info-data-link">
                        <span class="info-data-top"><b>{{order_count}}</b>个</span>
                        <span class="info-data-bottom">完成订单</span>
                    </router-link>
                    <router-link to="/benefit" tag="li" class="info-data-link">
                        <span class="info-data-top"><b>{{balance_amount}}</b>元</span>
                        <span class="info-data-bottom">总收益</span>
                    </router-link>
                    <router-link to="/points" tag="li" class="info-data-link">
                        <span class="info-data-top"><b>{{balance}}</b>元</span>
                        <span class="info-data-bottom">待结算</span>
                    </router-link>
                </ul>
            </section>
            <section class="profile-1reTe">
                <div class="myorder" v-if="!this.userInfo">
                    <span>请先登录，并绑定手机号</span>
                </div>

                <!-- 我的订单 -->
                <div class="myorder" v-if="(this.userInfo && this.userInfo.work_status == 0)" @click="goonline">
                <!-- <div class="myorder" @click="goonline"> -->
                    <aside>
                        <svg fill="#4aa5f0">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#order"></use>
                        </svg>
                    </aside>
                    <div class="myorder-div">
                        <span>上线抢单</span>
                        <span class="myorder-divsvg">
                            <svg fill="#bbb">
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                            </svg>
                        </span>
                    </div>
                </div>
                <!-- <router-link to='/order' class="myorder" v-if="(this.userInfo.work_status == 0)">
                    
                </router-link> -->
                <!-- 获取订单 -->
                <!-- <router-link to='/order' class="myorder" v-if="this.userInfo"> -->
                <router-link to='/orderDetail' class="myorder" v-if="this.userInfo && this.userInfo.sending_order_id > 0">
                    <aside>
                        <svg fill="#4aa5f0">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#vip"></use>
                        </svg>
                    </aside>
                    <div class="myorder-div">
                        <span>我的订单</span>
                        <span class="myorder-divsvg">
                            <svg fill="#bbb">
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                            </svg>
                        </span>
                    </div>
                </router-link>
                <!-- 下线休息 -->
                <div class="myorder" @click="gooffline" v-if="(this.userInfo && this.userInfo.work_status == 1)">
                    <!-- <div class="myorder" @click="gooffline"> -->
                    <aside>
                        <svg fill="#4aa5f0">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#point"></use>
                        </svg>
                    </aside>
                    <div class="myorder-div">
                        <span>下线休息</span>
                        <span class="myorder-divsvg">
                            <svg fill="#bbb">
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-right"></use>
                            </svg>
                        </span>
                    </div>
                </div>
            </section>
        </section>
        <foot-guide></foot-guide>
        <transition name="router-slid" mode="out-in">
            <router-view></router-view>
        </transition>
        <alert-tip v-if="showAlert" :showHide="showAlert" @closeTip="closeTip" :alertText="alertText"></alert-tip>
    </div>
</template>

<script>
import headTop from 'src/components/header/head'
import footGuide from 'src/components/footer/footGuide'
import alertTip from 'src/components/common/alertTip'
import {mapState, mapMutations} from 'vuex'
import {imgBaseUrl} from 'src/config/env'
import {getImgPath} from 'src/components/common/mixin'
import {rideronline,rideroffline,cityGuess} from 'src/service/getData'

export default {
    data(){
        return{
            profiletitle: '骑手信息',
            username: '登录/注册',           //用户名
            resetname: '',
            mobile: '暂无绑定手机号',             //电话号码
            balance: 0,            //待结算
            order_count : 0,             //完成订单数
            balance_amount : 0,       //总收益
            avatar: '',             //头像地址
            imgBaseUrl,
            showAlert: false, //显示提示组件
            alertText: null, //提示的内容
            riderid: '',
            guessCity: '',   //当前城市
            guessCityid: '', //当前城市id
        }
    },
    mounted(){
        this.initData();
        // 获取当前城市
        cityGuess().then(res => {
            this.guessCity = res.name;
            this.guessCityid = res.id;
        })
    },
    activated(){
        this.initData();
        // 获取当前城市
        cityGuess().then(res => {
            this.guessCity = res.name;
            this.guessCityid = res.id;
        })
    },
    mixins: [getImgPath],
    components:{
        headTop,
        footGuide,
        alertTip,
        cityGuess
    },

    computed:{
        ...mapState([
            'userInfo',
        ]),
        //后台会返回两种头像地址格式，分别处理
        imgpath:function () {
            let path;
            if(this.avatar.indexOf('/') !==-1){
                path = imgBaseUrl +　this.avatar;
            }else{
                path = this.getImgPath(this.avatar)
            }
            this.SAVE_AVANDER(path);
            return path;
        }
    },

    methods:{
        ...mapMutations([
            'SAVE_AVANDER','RECORD_USERINFO'
        ]),
        initData(){
            if (this.userInfo && this.userInfo.rider_id) {
                this.avatar = this.userInfo.avatar;
                this.username = this.userInfo.rider_name;
                this.mobile = this.userInfo.mobile || '暂无绑定手机号';
                this.balance = this.userInfo.balance;
                this.order_count = this.userInfo.order_count;
                this.balance_amount = this.userInfo.balance_amount;
                this.riderid = this.userInfo.rider_id
            }else{
                this.username = '登录/注册';
                this.mobile = '暂无绑定手机号';
            }
        },
        async goonline(){
            if(!this.userInfo){
                this.showAlert=true
                this.alertText="请先登录"
                return
            }
            if(!this.userInfo.mobile){
                this.showAlert=true
                this.alertText="请先绑定手机号"
                return
            }
            if(1 == this.userInfo.work_status){
                this.showAlert=true
                this.alertText="你已上线"
                return
            }
            let res = await rideronline(this.riderid)
            if(res && res.rider_id){
                this.RECORD_USERINFO(res)
                this.$router.push("/city/"+this.guessCityid)
            }
        },
        async gooffline(){
            if(!this.userInfo){
                this.showAlert=true
                this.alertText="请先登录"
                return
            }
            if(!this.userInfo.mobile){
                this.showAlert=true
                this.alertText="请先绑定手机号"
                return
            }
            if(0 == this.userInfo.work_status){
                this.showAlert=true
                this.alertText="你已下线"
                return
            }
            let res = await rideroffline(this.riderid)
            if(res && res.error){
                this.showAlert=true
                this.alertText="res.error"
            }else if(res.rider_id){
                this.RECORD_USERINFO(res)
                this.initData()
                // this.$router.push("/city/"+this.guessCityid)
            }
        },
        closeTip(){
            this.showAlert = false;
        }
    },
    watch: {
        userInfo: function (value){
            this.initData()
        }
    }
}

</script>

<style lang="scss" scoped>
   @import 'src/style/mixin';

    .profile_page{
        p, span{
            font-family: Helvetica Neue,Tahoma,Arial;
        }
    }
   .profile-number{
        padding-top:1.95rem;
        .profile-link{
            display:block;
            display:flex;
            box-align: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            align-items: center;
            background:$blue;
            padding: .666667rem .6rem;
            .privateImage{
                display:inline-block;
                @include wh(2.5rem,2.5rem);
                border-radius:50%;
                vertical-align:middle;
                .privateImage-svg{
                    background:$fc;
                    border-radius:50%;
                    @include wh(2.5rem,2.5rem);
                }
            }
            .user-info{
                margin-left:.48rem;
                -webkit-box-flex: 1;
                -ms-flex-positive: 1;
                flex-grow: 1;
                p{
                    font-weight:700;
                    @include sc(.8rem,$fc);
                    .user-icon{
                        @include wh(0.5rem,0.75rem);
                        display:inline-block;
                        vertical-align:middle;
                        line-height:0.75rem;
                        .icon-mobile{
                            @include wh(100%,100%);
                        }
                    }
                    .icon-mobile-number{
                        display:inline-block;
                        @include sc(.57333rem,$fc);

                    }
                }

            }
            .arrow{
                @include wh(.46667rem,.98rem);
                display:inline-block;
                svg{
                   @include wh(100%,100%);
                }
            }
        }
   }
   .info-data{
        width:100%;
        background:$fc;
        box-sizing: border-box;
        ul{
            .info-data-link{
                float:left;
                width:33.33%;
                display:inline-block;
                border-right:1px solid #f1f1f1;
                span{
                    display:block;
                    width:100%;
                    text-align:center;
                }
                .info-data-top{
                    @include sc(.55rem,#333);
                    padding: .853333rem 0 .453333rem;
                    b{
                        display:inline-block;
                        @include sc(1.2rem,#f90);
                        font-weight:700;
                        line-height:1rem;
                        font-family: Helvetica Neue,Tahoma;
                    }
                }
                .info-data-bottom{
                    @include sc(.57333rem,#666);
                    font-weight:400;
                    padding-bottom:.453333rem;

                }
            }
            .info-data-link:nth-of-type(2){
                .info-data-top{
                    b{
                        color:#ff5f3e;
                    }
                }

            }
            .info-data-link:nth-of-type(3){
                border:0;
                .info-data-top{
                    b{
                        color:#6ac20b;
                    }
                }
            }
        }
   }
   .profile-1reTe{
        margin-top:.4rem;
        background:$fc;
        .myorder{
            padding-left:1.6rem;
            display:flex;
            align-items: center;
            aside{
                @include wh(.7rem,.7rem);
                margin-left:-.866667rem;
                margin-right:.266667rem;
                display:flex;
                align-items: center;
                svg{
                    @include wh(100%,100%);
                }
            }
            .myorder-div{
                width:100%;
                border-bottom:1px solid #f1f1f1;
                padding:.433333rem .266667rem .433333rem 0;
                @include sc(.7rem,#333);
                display:flex;
                justify-content:space-between;
                span{
                    display:block;
                }
                .myorder-divsvg{
                    @include wh(.46667rem,.466667rem);
                    svg{
                        @include wh(100%,100%);
                    }
                }
            }
        }
        .myorder:nth-of-type(3) .myorder-div{
            border:0;
        }
    }
    .router-slid-enter-active, .router-slid-leave-active {
        transition: all .4s;
    }
    .router-slid-enter, .router-slid-leave-active {
        transform: translate3d(2rem, 0, 0);
        opacity: 0;
    }
</style>
