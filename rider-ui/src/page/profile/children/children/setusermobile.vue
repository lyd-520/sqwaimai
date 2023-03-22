 <template>
    <div class="rating_page">
        <head-top head-title="修改手机号" go-back='true'></head-top>
        <section class="setname">
            <section class="setname-top">
                <input type="text" placeholder="输入手机号" :class="{'setname-input':bordercolor}" @input="inputThing" v-model="inputValue">
                <div>
                    <p v-if="earn">手机号将用于与客户联系，请谨慎录入</p>
                    <p class="unlikep" v-else>手机号长度在11到13位之间</p>
                </div>
            </section>
            <section class="reset">
                <button :class="{fontopacity:opacityall}" @click="resetmobile">确认修改</button>
            </section>
        </section>
        <alert-tip v-if="showAlert" :showHide="showAlert" @closeTip="closeTip" :alertText="alertText"></alert-tip>
    </div>
</template>

<script>
    import {changemobile} from 'src/service/getData'
    import headTop from 'src/components/header/head'
    import {getImgPath} from 'src/components/common/mixin'
    import {mapMutations,mapState} from 'vuex'
    import alertTip from 'src/components/common/alertTip'
    export default {
      data(){
            return{
                earn: true,     //输入框提醒
                bordercolor: false,  //输入框边框颜色
                opacityall: false,   //字体透明度
                inputValue: '',       //输入框的内容
                newusermobile: '',         //新用户名
                showAlert: false, //显示提示组件
                alertText: null //提示的内容
            }
        },
        mixins: [getImgPath],
        components: {
            headTop,
            alertTip
        },
        computed:{
            ...mapState([
                'userInfo'
            ])
        },
        methods: {
            ...mapMutations([
                'RESET_MOBILE'
            ]),
            async inputThing(){
                if(this.inputValue.length <11 || this.inputValue.length>12){
                    this.earn=false;
                    this.bordercolor=true;
                    this.opacityall=false;
                    return false;
                }else{
                    this.earn=true;
                    this.bordercolor=false;
                    this.opacityall=true;
                    return true;
                }
            },
            async resetmobile(){
                let checkResult = this.inputThing();
                if (!checkResult) {
                  return;
                }
                let res = await changemobile(this.userInfo.rider_id,this.inputValue);
                if(res){
                    this.RESET_MOBILE(this.inputValue);
                    this.$router.go(-1);
                }else{
                    this.showAlert = true;
                    this.alertText = '密码修改失败';
                }

            },
            closeTip(){
                this.showAlert = false;
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import 'src/style/mixin';

    .rating_page{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 202;
        background:#f2f2f2;
        padding-top: 1.95rem;
        p, span{
            font-family: Helvetica Neue,Tahoma,Arial;
        }
    }
    .setname{
        width:15.2rem;
        margin:0 auto;
        .setname-top{
            padding-top:.4rem;
            input{
                background:none;
                width:15.2rem;
                border:1px solid #ddd;
                @include borderRadius(2px);
                padding:.2rem .1rem;
                line-height:1.2rem;
                font-size:.7rem;
                display:block;
            }
            .setname-input{
                border-color:#ea3106;
            }
            p{
                width:100%;
                @include sc(.4rem,#666);
                padding:.4rem 0 1rem;
            }
            .unlikep{
                @include sc(.58rem,#ea3106);
                padding-top:.1rem;
            }
        }
        .reset{
            width:100%;
            background:#3199e8;
            button{
                display:block;
                width:100%;
                background:none;
                line-height:2rem;
                @include sc(.7rem,#fff);
                opacity:.6;
                transition: all 1s;
            }
            .fontopacity{
                transition: all 1s;
                opacity:1;
            }
        }
    }
</style>
