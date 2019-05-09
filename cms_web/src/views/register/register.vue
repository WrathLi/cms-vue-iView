<style lang="less">
    @import "./register.less";
</style>

<template>
    <div class="register">
        <div class="register-body-con">
            <div class="main_card">
                <div class="main_card-title">
                    <h3>用户注册</h3>
                </div>
                <div class="info-panel">
                    <Row type="flex" justify="start">
                        <Col span="4" offset="1">
                        <div class="my_modal_tip_label">
                            <label>用户名:</label>
                        </div>
                        </Col>
                        <Col span="8">
                        <Input v-model="optRegister.userId" placeholder="由英文字符、数字或下划线组成,不超过20位" class="my_modal_input_large" size="large">

                        </Input>
                        </Col>
                    </Row>
                    <br>
                    <Row type="flex" justify="start">
                        <Col span="4" offset="1">
                        <div class="my_modal_tip_label">
                            <label>昵称:</label>
                        </div>
                        </Col>
                        <Col span="8">
                        <Input v-model="optRegister.nickname" placeholder="昵称尽量简单" class="my_modal_input_large" size="large">

                        </Input>
                        </Col>
                    </Row>
                    <br>
                    <Row type="flex" justify="start">
                        <Col span="4" offset="1">
                        <div class="my_modal_tip_label">
                            <label>密码:</label>
                        </div>
                        </Col>
                        <Col span="8">
                        <Input v-model="optRegister.pwd" type="password" placeholder="6~20位(英文或数字组成)" class="my_modal_input_large" size="large">

                        </Input>
                        </Col>
                    </Row>
                    <br>
                    <Row type="flex" justify="start">
                        <Col span="4" offset="1">
                        <div class="my_modal_tip_label">
                            <label>重复密码:</label>
                        </div>
                        </Col>
                        <Col span="8">
                        <Input v-model="optRegister.checkPwd" type="password" placeholder="再次输入密码" class="my_modal_input_large" size="large">

                        </Input>
                        </Col>
                    </Row>
                </div>
                <div class="error_tip">
                    <label>{{optRegister.errorMsg}}</label>
                </div>
                <div class="bottom-panel">
                    <Button class="bottom-panel-btn" style="margin-right:20px;" @click="backToLogin">返回登录页</Button>
                    <Button class="bottom-panel-btn" type="info" @click="onRegister">注册</Button>
                </div>
            </div>
        </div>
        <Modal class="register_success_modal" v-model="succeedModal" width="360">
            <p slot="header" style="color:#19be6b;text-align: center;">
                <Icon type="checkmark-circled"></Icon>
                <span>注册成功</span>
            </p>
            <div style="text-align: center;">
                <p>用户注册成功!</p>
                <p>用户角色为游客.</p>
            </div>
            <div slot="footer">
                <Button type="success" size="large" long @click="backToLogin">返回登录页</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import safeHttp from '../../libs/safe_http';
    import CommonUrl from '../common/common_url';
    import util from '../../libs/util';
    import JSEncrypt from 'jsencrypt';

    export default {
        data () {
            return {
                optRegister: {
                    errorMsg: '',
                    userId: '',
                    nickname: '',
                    pwd: '',
                    checkPwd: ''
                },
                succeedModal: false
            };
        },
        mounted () {
            this.init();
        },
        methods: {
            init () {
                this.getPublicKey();
            },
            getPublicKey () {
                safeHttp.get(CommonUrl.PUBLIC_KEY, {}, function (data, status) {
                    let jsData = data;
                    if (jsData.code === 1) {
                        let publicKey = jsData.publicKey;
                        Cookies.set('publicKey', publicKey);
                    }
                });
            },
            onRegister () {
                this.optRegister.errorMsg = '';
                let userId = this.optRegister.userId;
                let nickname = this.optRegister.nickname;
                let pwd = this.optRegister.pwd;
                let checkPwd = this.optRegister.checkPwd;
                if (util.isNull(userId)) {
                    this.optRegister.errorMsg = '用户名不能为空';
                    return;
                } else if (userId.length > 20) {
                    this.optRegister.errorMsg = '用户名不能超过20位';
                    return;
                }
                if (util.isNull(nickname)) {
                    this.optRegister.errorMsg = '请输入昵称';
                    return;
                } else if (nickname.length > 20) {
                    this.optRegister.errorMsg = '昵称不能超过20位';
                    return;
                }
                if (util.isNull(pwd)) {
                    this.optRegister.errorMsg = '请输入密码';
                    return;
                } else if (pwd.length < 6 || pwd.length > 20) {
                    this.optRegister.errorMsg = '密码长度6~20位';
                    return;
                }
                if (util.isNull(checkPwd)) {
                    this.optRegister.errorMsg = '请再次输入密码';
                    return;
                } else if (!(pwd === checkPwd)) {
                    this.optRegister.errorMsg = '两次输入的密码不匹配';
                    return;
                }
                let publicKey = Cookies.get('publicKey');
                if (util.isNull(publicKey)) {
                    this.optRegister.errorMsg = '网络错误,请刷新页面';
                    return;
                }
                let encrypt = new JSEncrypt();
                encrypt.setPublicKey(publicKey);
                const userRSA = encrypt.encrypt(userId);
                const pwdRSA = encrypt.encrypt(pwd);
                let param = {
                    userId: userRSA,
                    password: pwdRSA,
                    nickname: nickname
                };
                let vm = this;
                safeHttp.post(CommonUrl.REGISTER_URL,param,function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.succeedModal = true;
                    } else {
                        vm.optRegister.errorMsg = msg;
                    }
                });
            },
            backToLogin () {
                this.succeedModal = false;
                this.$router.back(-1);
            }
        }
    }
</script>

