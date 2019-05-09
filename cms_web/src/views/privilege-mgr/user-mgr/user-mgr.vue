<style lang="less">
    @import "./user-mgr.less";
</style>
<template>
    <div class="user-mgr">
        <div class="user-mgr-body-con">
            <div class="user_opt_panel">
                <Row type="flex" justify="start">
                    <Col span="1.5">
                    <div class="my_opt_wrapper" style="margin-left:20px;">
                        <div class="my_opt_wrapper-cell">
                            <label class="my_panel_label">关键字:</label>
                        </div>
                    </div>
                    </Col>
                    <Col span="5.5">
                    <div class="my_opt_wrapper">
                        <div class="my_opt_wrapper-cell">
                            <Input v-model="searchParam.searchKey" placeholder="输入用户名或昵称(多个用逗号分隔)"
                                   class="name_search_input" size="large" @on-enter="onSearch">
                            </Input>
                        </div>
                    </div>
                    </Col>
                    <Col span="1.5">
                    <div class="my_opt_wrapper" style="margin-left:20px;">
                        <div class="my_opt_wrapper-cell">
                            <label class="my_panel_label">状态:</label>
                        </div>
                    </div>
                    </Col>
                    <Col span="2">
                    <div class="my_opt_wrapper">
                        <div class="my_opt_wrapper-cell">
                            <Select v-model="searchParam.filterStatus" style="width:80px;">
                                <Option v-for="item in statusList" :value="item.value" :key="item.value">{{item.title}}</Option>
                            </Select>
                        </div>
                    </div>
                    </Col>
                    <Col span="2">
                    <div class="my_opt_wrapper">
                        <div class="my_opt_wrapper-cell">
                            <Button class="opt_btn_text_size" type="primary" icon="ios-search" @click="onSearch">查询</Button>
                        </div>
                    </div>
                    </Col>
                    <Col span="3">
                    <div class="my_opt_wrapper">
                        <div class="my_opt_wrapper-cell">
                            <Button class="opt_btn_text_size" type="success" icon="ios-plus" @click="addUser">新增用户</Button>
                        </div>
                    </div>
                    </Col>
                </Row>
            </div>
            <br>
            <div class="role_table_con">
                <Table border :columns="tableColumns" :data="tableData.rows"></Table>
                <br>
                <Page class="table_page" :total="tableData.total" :page-size="pageSize" :current="pageIndex" show-total @on-change="changePage"></Page>
            </div>
            <br>
        </div>
        <Modal :closable="false" :title="optUser.modalTitle" v-model="editUserModal" class-name="edit_user_modal"
               scrollabel="true">
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label style="color:red;">注意:</label>
                </div>
                </Col>
                <Col span="20">
                <div class="my_modal_tip_label" style="text-align: left;">
                    <label style="color:red;">新增用户默认密码为:123456,进入个人中心修改密码.</label>
                </div>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>用户名:</label>
                </div>
                </Col>
                <Col span="8">
                <div v-if="optUser.optType === 'add'">
                    <Input v-model="optUser.optData.userId" placeholder="输入用户名" class="my_modal_input_large" size="large">

                    </Input>
                </div>
                <div v-else>
                    <Input v-model="optUser.optData.userId" placeholder="输入用户名" class="my_modal_input_large" size="large" disabled>

                    </Input>
                </div>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>昵称:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optUser.optData.nickname" placeholder="请输入昵称" class="my_modal_input_large" size="large">
                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>角色:</label>
                </div>
                </Col>
                <Col span="8">
                <Select v-model="optUser.optData.roleId" style="width:200px;" filterable>
                    <Option v-for="item in optRoleList" :value="item.value" :key="item.value"
                            placeholder="请选择角色">{{item.title}}</Option>
                </Select>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>电话:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optUser.optData.phoneNumber" placeholder="请输入电话号码" class="my_modal_input_large" size="large">
                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>Email:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optUser.optData.email" placeholder="请输入Email" class="my_modal_input_large" size="large">
                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>冻结:</label>
                </div>
                </Col>
                <Col span="3">
                <div class="edit_page_model_check">
                    <Checkbox v-model="optUser.optData.freezed" size="large">
                    </Checkbox>
                </div>
                </Col>
            </Row>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optUser.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="cancelUser">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="primary" class="my_modal_btn" @click="saveUser">
                        保存
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>
        <Modal :closable="false" title="删除用户" v-model="deleteUserModal" class-name="delete_user_modal"
               scrollabel="true">
            <div class="delete_modal_content_label">
                <label>是否删除[{{optUser.optData.userId}}]这个用户?</label>
            </div>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optUser.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="cancelDelete">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="error" class="my_modal_btn" @click="deleteUser">
                        删除
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>
        <br>
    </div>
</template>
<script>
    import util from '../../../libs/util';
    import safeHttp from '../../../libs/safe_http';
    import CommonUrl from '../../common/common_url';

    export default {
        data () {
            return {
                searchParam: {
                    searchKey: '',
                    filterStatus: -1,
                    isInit: true
                },
                statusList: [
                    {
                        value: -1,
                        title: '--'
                    },
                    {
                        value: 0,
                        title: '正常'
                    },
                    {
                        value: 1,
                        title: '冻结'
                    }
                ],
                optRoleList: [],
                optUser: {
                    optType: 'add',
                    modalTitle: '',
                    errorMsg: '',
                    optData: {
                        id: null,
                        userId: '',
                        nickname: '',
                        phoneNumber: '',
                        email: '',
                        roleId: 0,
                        freezed: false
                    }
                },
                editUserModal: false,
                deleteUserModal: false,
                pageIndex: 1,
                pageSize: 10,
                tableData: {
                    total: 0,
                    rows: []
                },
                tableColumns: [
                    {
                        title: 'ID',
                        key: 'id',
                        width: 80
                    },
                    {
                        title: '用户名',
                        key: 'userId',
                        width: 200
                    },
                    {
                        title: '昵称',
                        key: 'nickname',
                        width: 200
                    },
                    {
                        title: '角色',
                        key: 'roleName',
                        width: 180
                    },
                    {
                        title: '状态',
                        key: 'freezed',
                        width: 120,
                        render: (h, params) => {
                            let item = params.row;
                            let freezed = item['freezed'];
                            let tag = '正常';
                            let tagColor = 'green';
                            if (freezed === true) {
                                tag = '冻结';
                                tagColor = 'red';
                            }
                            return h('div', [
                                h('Tag', {
                                    props: {
                                        color: tagColor,
                                        size: 'large'
                                    }
                                }, tag)
                            ]);
                        }
                    },
                    {
                        title: 'Action',
                        key: 'id',
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '10px'
                                    },
                                    on: {
                                        click: () => {
                                            this.editUser(params.row);
                                        }
                                    }
                                }, '编辑用户'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.openDelete(params.row);
                                        }
                                    }
                                }, '删除用户')
                            ]);
                        }
                    }
                ]
            };
        },
        mounted () {
            this.init();
        },
        methods: {
            init () {
                this.reloadTable();
                this.getFilterRole();
            },
            onSearch () {
                this.searchParam.isInit = false;
                this.reloadTable();
            },
            getFilterRole () {
                let vm = this;
                safeHttp.jwtGet(CommonUrl.USER_MGR_ROLE_LIST_URL, {}, function (data, status) {
                    let jsData = data;
                    if (jsData.code >= 0) {
                        let roleList = jsData.data;
                        let tmpRoleList = [];
                        for (let i = 0, maxI = roleList.length; i < maxI; i++) {
                            let tmpItem = roleList[i];
                            let value = tmpItem['id'];
                            let title = tmpItem['name'];
                            let roleItem = {
                                value: value,
                                title: title
                            };
                            tmpRoleList.push(roleItem);
                        }
                        vm.optRoleList = tmpRoleList;
                    }
                }, vm);
            },
            reloadTable () {
                this.pageIndex = 1;
                this.getUserList();
            },
            refreshTable () {
                this.getUserList();
            },
            getUserList () {
                let searchKey = this.searchParam.searchKey;
                let isInit = this.searchParam.isInit;
                let filterStatus = this.searchParam.filterStatus;
                let page = this.pageIndex - 1;
                let pageSize = this.pageSize;
                let param = {
                    isInit: isInit,
                    searchKey: searchKey,
                    status: filterStatus,
                    pageIndex: page,
                    pageSize: pageSize
                };
                let vm = this;
                safeHttp.jwtGet(CommonUrl.USER_MGR_USER_LIST_URL, param, function (data, status) {
                    let jsData = data;
                    vm.tableData.total = jsData.total;
                    vm.tableData.rows = jsData.rows;
                }, vm);
            },
            changePage (page) {
                this.pageIndex = page;
                this.refreshTable();
            },
            addUser () {
                this.optUser.optType = 'add';
                this.optUser.modalTitle = '新增用户';
                this.optUser.errorMsg = '';
                this.optUser.optData = {
                    id: null,
                    userId: '',
                    nickname: '',
                    phoneNumber: '',
                    email: '',
                    roleId: '',
                    freezed: false
                };
                this.editUserModal = true;
            },
            editUser (user) {
                let beSuper = user['beSuper'];
                if (beSuper === true) {
                    this.$Notice.open({
                        title: '超管用户无法编辑',
                        desc: ''
                    });
                } else {
                    this.optUser.optType = 'edit';
                    this.optUser.modalTitle = '编辑用户';
                    this.optUser.errorMsg = '';
                    let id = user['id'];
                    let userId = user['userId'];
                    let nickname = user['nickname'];
                    let phoneNumber = user['phoneNumber'];
                    let email = user['email'];
                    let roleId = user['roleId'];
                    let freezed = user['freezed'];
                    this.optUser.optData = {
                        id: id,
                        userId: userId,
                        nickname: nickname,
                        phoneNumber: phoneNumber,
                        email: email,
                        roleId: roleId,
                        freezed: freezed
                    };
                    this.editUserModal = true;
                }
            },
            openDelete (user) {
                let beSuper = user['beSuper'];
                if (beSuper === true) {
                    this.$Notice.open({
                        title: '超管用户无法删除',
                        desc: ''
                    });
                } else {
                    this.optUser.optType = 'delete';
                    this.optUser.modalTitle = '删除用户';
                    this.optUser.errorMsg = '';
                    let id = user['id'];
                    let userId = user['userId'];
                    let nickname = user['nickname'];
                    let phoneNumber = user['phoneNumber'];
                    let email = user['email'];
                    let roleId = user['roleId'];
                    let freezed = user['freezed'];
                    this.optUser.optData = {
                        id: id,
                        userId: userId,
                        nickname: nickname,
                        phoneNumber: phoneNumber,
                        email: email,
                        roleId: roleId,
                        freezed: freezed
                    };
                    this.deleteUserModal = true;
                }
            },
            cancelUser () {
                this.optUser.errorMsg = '';
                this.optUser.optData = {};
                this.editUserModal = false;
            },
            saveUser () {
                this.optUser.errorMsg = '';
                let id = this.optUser.optData['id'];
                let userId = this.optUser.optData['userId'];
                let nickname = this.optUser.optData['nickname'];
                let roleId = this.optUser.optData['roleId'];
                let phoneNumber = this.optUser.optData['phoneNumber'];
                let email = this.optUser.optData['email'];
                let freezed = this.optUser.optData['freezed'];
                if (util.isNull(userId)) {
                    this.optUser.errorMsg = '请输入用户名';
                    return;
                }
                if (util.isNull(nickname)) {
                    this.optUser.errorMsg = '请输入昵称';
                    return;
                }
                if (util.isNull(roleId)) {
                    this.optUser.errorMsg = '请选择角色';
                    return;
                }
                if (this.optUser.optType === 'edit' && util.isNull(id)) {
                    return;
                }
                let url = '';
                if (this.optUser.optType === 'add') {
                    url = CommonUrl.USER_MGR_USER_ADD_URL;
                } else if (this.optUser.optType === 'edit') {
                    url = CommonUrl.USER_MGR_USER_EDIT_URL;
                }
                let param = {
                    id: id,
                    userId: userId,
                    nickname: nickname,
                    roleId: roleId,
                    phoneNumber: phoneNumber,
                    email: email,
                    freezed: freezed
                };

                let vm = this;
                safeHttp.jwtPost(url, param, function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.refreshTable();
                        vm.editUserModal = false;
                    } else {
                        vm.optUser.errorMsg = msg;
                    }
                }, vm);
            },
            cancelDelete () {
                this.optUser.errorMsg = '';
                this.optUser.optData = {};
                this.deleteUserModal = false;
            },
            deleteUser () {
                this.optUser.errorMsg = '';
                let id = this.optUser.optData['id'];
                if (util.isNull(id)) {
                    return;
                } else {
                    let param = {
                        id: id
                    };

                    let vm = this;
                    safeHttp.jwtPost(CommonUrl.USER_MGR_USER_DELETE_URL, param, function (data, status) {
                        let jsonObj = JSON.parse(data);
                        let code = jsonObj.code;
                        let msg = jsonObj.msg;
                        if (code >= 0) {
                            vm.refreshTable();
                            vm.deleteUserModal = false;
                        } else {
                            vm.optUser.errorMsg = msg;
                        }
                    }, vm);
                }
            }
        }
    }
</script>
