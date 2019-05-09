<style lang="less">
    @import './own-space.less';
</style>

<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="person"></Icon>
                个人信息
            </p>
            <div>
                <Form
                        ref="personForm"
                        :model="personForm"
                        :label-width="90"
                        label-position="right"
                        :rules="infoValidate"
                >
                    <FormItem label="用户名：">
                        <span>{{ personForm.userId }}</span>
                    </FormItem>
                    <FormItem label="角色：">
                        <span>{{ personForm.roleName }}</span>
                    </FormItem>

                    <FormItem label="昵称：" prop="username">
                        <div style="display:inline-block;width:300px;">
                            <Input v-model="personForm.nickname" ></Input>
                        </div>
                    </FormItem>
                    <FormItem label="电话：" prop="phoneNumber">
                        <div style="display:inline-block;width:300px;">
                            <Input v-model="personForm.phoneNumber" ></Input>
                        </div>
                    </FormItem>
                    <FormItem label="Email：" prop="email">
                        <div style="display:inline-block;width:300px;">
                            <Input v-model="personForm.email">

                            </Input>
                        </div>
                    </FormItem>

                    <FormItem label="登录密码：">
                        <Button type="warning" size="small" @click="showEditPassword">修改密码</Button>
                    </FormItem>
                    <div style="margin-left:80px;">
                        <Button style="width: 100px;margin-right:10px;" @click="cancelEditUserInfor">取消</Button>
                        <Button type="primary" style="width: 100px;" @click="saveEdit">保存</Button>
                    </div>
                </Form>
            </div>
        </Card>
        <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="500">
            <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right" :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码" ></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符" ></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input v-model="editPasswordForm.rePass" placeholder="请再次输入新密码" ></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">保存</Button>
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
        name: 'ownspace_index',
        data () {
            const valideNewPassword = (rule, value, callback) => {
                if (value === this.editPasswordForm.oldPass) {
                    callback(new Error('新密码不能和旧密码相同'));
                } else {
                    callback();
                }
            };
            const valideRePassword = (rule, value, callback) => {
                if (value !== this.editPasswordForm.newPass) {
                    callback(new Error('两次输入密码不一致'));
                } else {
                    callback();
                }
            };
            return {
                personForm: {
                    userId: '',
                    roleName: '',
                    nickname: '',
                    phoneNumber: '',
                    email: ''
                },
                uid: 0,
                editPasswordModal: false, // 修改密码模态框显示
                savePassLoading: false,
                oldPassError: '',
                infoValidate: {
                    nickname: [
                        { required: true, message: '请输入昵称', trigger: 'blur' }
                    ]
                },
                editPasswordForm: {
                    oldPass: '',
                    newPass: '',
                    rePass: ''
                },
                passwordValidate: {
                    oldPass: [
                        { required: true, message: '请输入原密码', trigger: 'blur' }
                    ],
                    newPass: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { min: 6, message: '请至少输入6个字符', trigger: 'blur' },
                        { max: 20, message: '最多输入20个字符', trigger: 'blur' },
                        { validator: valideNewPassword, trigger: 'blur' }

                    ],
                    rePass: [
                        { required: true, message: '请再次输入新密码', trigger: 'blur' },
                        { validator: valideRePassword, trigger: 'blur' }
                    ]
                }
            };
        },
        mounted () {
            this.init();
        },
        methods: {
            init () {
                let userId = Cookies.get('user');
                this.personForm.userId = userId;
                this.getPersonInfo();
                this.getPublicKey();
            },
            getPersonInfo () {
                let vm = this;
                let param = {
                    userId: this.personForm.userId
                };
                safeHttp.jwtGet(CommonUrl.PERSON_GET_INFO_URL, param, function (data, status) {
                    let jsData = data;
                    if (jsData.code >= 0) {
                        let personInfo = jsData.data;
                        let id = personInfo['id'];
                        let nickname = personInfo['nickname'];
                        let phoneNumber = personInfo['phoneNumber'];
                        let email = personInfo['email'];
                        let roleName = personInfo['roleName'];
                        vm.personForm.nickname = nickname;
                        vm.personForm.phoneNumber = phoneNumber;
                        vm.personForm.email = email;
                        vm.personForm.roleName = roleName;
                        vm.uid = id;
                    } else {
                        this.$Notice.open({
                            title: '请求用户信息失败',
                            desc: ''
                        });
                    }
                }, vm);
            },
            getPublicKey () {
                let publicKey = Cookies.get('publicKey');
                if (util.isNull(publicKey)) {
                    safeHttp.get(CommonUrl.PUBLIC_KEY, {}, function (data, status) {
                        let jsData = data;
                        if (jsData.code === 1) {
                            let publicKey = jsData.publicKey;
                            Cookies.set('publicKey', publicKey);
                        }
                    });
                }
            },
            showEditPassword () {
                this.editPasswordModal = true;
            },
            cancelEditUserInfor () {
                this.$store.commit('removeTag', 'ownspace_index');
                localStorage.pageOpenedList = JSON.stringify(this.$store.state.app.pageOpenedList);
                let lastPageName = '';
                if (this.$store.state.app.pageOpenedList.length > 1) {
                    lastPageName = this.$store.state.app.pageOpenedList[1].name;
                } else {
                    lastPageName = this.$store.state.app.pageOpenedList[0].name;
                }
                this.$router.push({
                    name: lastPageName
                });
            },
            saveEdit () {
                this.$refs['personForm'].validate((valid) => {
                    if (valid) {
                        let id = this.uid;
                        let nickname = this.personForm.nickname;
                        let phoneNumber = this.personForm.phoneNumber;
                        let email = this.personForm.email;
                        let param = {
                            id: id,
                            nickname: nickname,
                            phoneNumber: phoneNumber,
                            email: email
                        };
                        let vm = this;
                        safeHttp.jwtPost(CommonUrl.PERSON_EDIT_INFO_URL, param, function (data, status) {
                            let jsonObj = JSON.parse(data);
                            let code = jsonObj.code;
                            let msg = jsonObj.msg;
                            if (code >= 0) {
                                vm.$Message.success('保存成功');
                            } else {
                                vm.$Message.error(msg);
                            }
                        }, vm);
                    }
                });
            },
            cancelEditPass () {
                this.editPasswordModal = false;
            },
            saveEditPass () {
                this.$refs['editPasswordForm'].validate((valid) => {
                    if (valid) {
                        this.savePassLoading = true;
                        // you can write ajax request here
                        let publicKey = Cookies.get('publicKey');
                        if (util.isNull(publicKey)) {
                            this.$Message.error('网络错误,请刷新页面');
                        } else {
                            let pwd = this.editPasswordForm.newPass;
                            let id = this.uid;
                            let encrypt = new JSEncrypt();
                            encrypt.setPublicKey(publicKey);
                            const pwdRSA = encrypt.encrypt(pwd);
                            let param = {
                                id: id,
                                password: pwdRSA
                            };
                            let vm = this;
                            safeHttp.jwtPost(CommonUrl.PERSON_CHANGE_PWD_URL, param, function (data, status) {
                                let jsonObj = JSON.parse(data);
                                let code = jsonObj.code;
                                let msg = jsonObj.msg;
                                if (code >= 0) {
                                    vm.savePassLoading = false;
                                    vm.editPasswordModal = false;
                                    vm.$Message.success('密码修改成功');
                                } else {
                                    vm.savePassLoading = false;
                                    vm.$Message.error(msg);
                                }
                            }, vm);
                        }
                    }
                });
            }
        }
    };
</script>
