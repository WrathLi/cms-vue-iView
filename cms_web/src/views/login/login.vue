<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    登录后台管理系统
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                            <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                            <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <div style="margin-bottom:10px;">
                            <label class="pull-left error_tip">{{myTmp.errorMsg}}</label>
                        </div>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long style="font-size: 14px;">登录</Button>
                        </FormItem>

                    </Form>
                </div>
                <div id="regist_btn" style="width: 268px;margin-top: -15px;">
                    <Button @click="handleRegist" type="success" long style="font-size: 14px;">注册</Button>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import safeHttp from '../../libs/safe_http';
    import CommonUrl from '../common/common_url';
    import ReturnCode from '../common/return_code';
    import util from '../../libs/util';
    import JSEncrypt from 'jsencrypt';
    export default {
        data () {
            return {
                form: {
                    userName: '',
                    password: ''
                },
                pageData: {
                    publicKey: ''
                },
                myTmp: {
                    errorMsg: ''
                },
                rules: {
                    userName: [
                        { required: true, message: '账号不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' }
                    ]
                }
            };
        },
        created () {
            this.getPublicKey();
        },
        methods: {

            getPublicKey () {
                safeHttp.get(CommonUrl.PUBLIC_KEY, {}, function (data, status) {
                    let jsData = data;
                    if (jsData.code === 1) {
                        let publicKey = jsData.publicKey;
                        Cookies.set('publicKey', publicKey);
                    }
                });
            },

            handleSubmit () {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        const username = this.form.userName;
                        const password = this.form.password;
                        const publicKey = Cookies.get('publicKey');
                        if (util.isNull(publicKey)) {
                            this.myTmp.errorMsg = '网络错误,请刷新页面';
                        } else {
                            let encrypt = new JSEncrypt();
                            encrypt.setPublicKey(publicKey);
                            const userRSA = encrypt.encrypt(username);
                            const pwdRSA = encrypt.encrypt(password);
                            let param = {
                                username: userRSA,
                                password: pwdRSA
                            };
                            let vm = this;
                            safeHttp.post(CommonUrl.LOGIN_URL, param, function (data, status) {
                                let jsonObj = JSON.parse(data);
                                let code = jsonObj.code;
                                let menu = jsonObj.menu;
                                if (code === ReturnCode.LOGIN_SUCCEED_CODE) {
                                    let userInfo = jsonObj['userInfo'];
                                    const userId = userInfo['userName'];
                                    const nickname = userInfo['nickname'];
                                    const loginId = jsonObj['loginId'];
                                    const token = jsonObj['token'];
                                    let name = util.isNull(nickname) ? userId : nickname;
                                    Cookies.set('user', userId);
                                    Cookies.set('nickname', name);
                                    Cookies.set('access', 1);
                                    Cookies.set('menu', menu);
                                    Cookies.set('loginId', loginId);
                                    Cookies.set('token', token);
                                    vm.$store.commit('setAvator', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3448484253,3685836170&fm=27&gp=0.jpg');
                                    vm.$store.commit('updateMenulist');
                                    vm.$router.push({
                                        name: 'home_index'
                                    });
                                } else if (code === ReturnCode.ERROR_LOGIN_NOT_USER_CODE) {
                                    vm.myTmp.errorMsg = '用户不存在';
                                    Cookies.set('access', 0);
                                } else if (code === ReturnCode.ERROR_LOGIN_WRONG_PWD_CODE) {
                                    vm.myTmp.errorMsg = '密码错误';
                                    Cookies.set('access', 0);
                                } else if (code === ReturnCode.ERROR_LOGIN_USER_FREEZED_CODE) {
                                    vm.myTmp.errorMsg = '账号被冻结,请联系管理员';
                                    Cookies.set('access', 0);
                                } else {
                                    vm.myTmp.errorMsg = '登录错误';
                                    Cookies.set('access', 0);
                                }
                            });
                        }
                    }
                });
            },
            handleRegist () {
                this.$router.push({
                    name: 'register'
                });
            }
        }
    };
</script>

<style>

</style>

