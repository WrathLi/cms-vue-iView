<style lang="less">
    @import "./role-mgr.less";
</style>

<template>
    <div class="role-mgr">
        <div class="role-mgr-body-con">
            <div class="role_opt_panel">
                <Row type="flex" justify="start">
                    <Col span="2">
                        <div class="my_opt_wrapper" style="margin-left:20px;">
                            <div class="my_opt_wrapper-cell">
                                <label class="my_panel_label">角色名称:</label>
                            </div>
                        </div>
                    </Col>
                    <Col span="6">
                        <div class="my_opt_wrapper">
                            <div class="my_opt_wrapper-cell">
                                <Input v-model="searchParam.searchKey" placeholder="输入角色名称(多个用逗号分隔)"
                                       class="name_search_input" size="large" @on-enter="onSearch">
                                </Input>
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
                            <Button class="opt_btn_text_size" type="success" icon="ios-plus" @click="addRole">新增角色</Button>
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
                <br>
            </div>
        </div>
        <Modal :closable="false" :title="optRole.modalTitle" v-model="editRoleModal" class-name="edit_role_modal"
               scrollabel="true">
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>名称:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optRole.optData.name" placeholder="输入角色名称" class="my_modal_input_large" size="large">

                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>描述:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optRole.optData.description" placeholder="角色描述" class="my_modal_input_large" size="large"
                    type="textarea" :rows="4">
                </Input>
                </Col>
            </Row>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optRole.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="onCancelRole">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="primary" class="my_modal_btn" @click="saveRole">
                        保存
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>

        <Modal :closable="false" :title="optPrivilege.modalTitle" v-model="editPrivilegeModal" class-name="edit_privilege_modal"
               scrollabel="true">
            <Tree :data="showPrivilegeData" show-checkbox multiple size="large" ref="tree"></Tree>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optPrivilege.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="cancelAssign">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="primary" class="my_modal_btn" @click="saveAssign">
                        保存
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>
        <br>
    </div>
</template>

<script>
    import safeHttp from '../../../libs/safe_http';
    import util from '../../../libs/util';
    import CommonUrl from '../../common/common_url';

    export default {
        data () {
            return {
                originPrivilegeData: [],
                showPrivilegeData: [],
                editPrivilegeModal: false,
                optPrivilege: {
                    optType: 'edit_privilege',
                    modalTitle: '',
                    errorMsg: '',
                    roleId: 0
                },
                optRole: {
                    optType: 'add',
                    modalTitle: '',
                    errorMsg: '',
                    optData: {
                        id: null,
                        name: '',
                        description: ''
                    }
                },
                editRoleModal: false,
                searchParam: {
                    isInit: true,
                    searchKey: ''
                },
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
                        title: '角色名称',
                        key: 'name',
                        width: 300
                    },
                    {
                        title: '描述',
                        key: 'description',
                        width: 400
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
                                            this.editRole(params.row);
                                        }
                                    }
                                }, '编辑角色'),
                                h('Button', {
                                    props: {
                                        type: 'warning',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.assignPrivilege(params.row);
                                        }
                                    }
                                }, '分配权限')
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
                this.getMenuTree();
            },
            onSearch () {
                this.searchParam.isInit = false;
                this.reloadTable();
            },
            getMenuTree () {
                let vm = this;
                safeHttp.jwtGet(CommonUrl.ROLE_MGR_PRIVILEGE_TREE_URL, {}, function (data, status) {
                    let jsData = data;
                    if (jsData.code >= 0) {
                        let menuTree = jsData.data;
                        let tmpPriTree = [];
                        for (let i = 0, maxI = menuTree.length; i < maxI; i++) {
                            let menuItem = menuTree[i];
                            const parentTitle = menuItem['title'];
                            let pageList = menuItem['children'];
                            let leafList = [];
                            for (let j = 0, maxJ = pageList.length; j < maxJ; j++) {
                                let pageItem = pageList[j];
                                const leafTitle = pageItem['title'];
                                const pageId = pageItem['pageId'];
                                let leafItem = {
                                    title: leafTitle,
                                    pageId: pageId,
                                    checked: false
                                };
                                leafList.push(leafItem);
                            }
                            let parentItem = {
                                title: parentTitle,
                                expand: true,
                                children: leafList
                            };
                            tmpPriTree.push(parentItem);
                        }
                        vm.originPrivilegeData = tmpPriTree;
                    }
                }, vm);
            },
            refreshTable () {
                this.getRoleList();
            },
            reloadTable () {
                this.pageIndex = 1;
                this.getRoleList();
            },
            getRoleList () {
                let searchKey = this.searchParam.searchKey;
                let isInit = this.searchParam.isInit;
                let page = this.pageIndex - 1;
                let pageSize = this.pageSize;
                let param = {
                    isInit: isInit,
                    searchKey: searchKey,
                    pageIndex: page,
                    pageSize: pageSize
                };
                let vm = this;
                safeHttp.jwtGet(CommonUrl.ROLE_MGR_ROLE_LIST_URL, param, function (data, status) {
                    let jsData = data;
                    vm.tableData.total = jsData.total;
                    vm.tableData.rows = jsData.rows;
                }, vm);
            },
            changePage (page) {
                this.pageIndex = page;
                this.refreshTable();
            },
            addRole () {
                this.optRole.optType = 'add';
                this.optRole.modalTitle = '新增角色';
                this.optRole.errorMsg = '';
                this.optRole.optData = {
                    id: null,
                    name: '',
                    description: ''
                };
                this.editRoleModal = true;
            },
            editRole (role) {
                this.optRole.optType = 'edit';
                this.optRole.modalTitle = '编辑角色';
                this.optRole.errorMsg = '';
                let id = role['id'];
                let name = role['name'];
                let description = role['description'];
                this.optRole.optData = {
                    id: id,
                    name: name,
                    description: description
                };
                this.editRoleModal = true;
            },
            saveRole () {
                this.optRole.errorMsg = '';
                let id = this.optRole.optData['id'];
                let name = this.optRole.optData['name'];
                let description = this.optRole.optData['description'];
                if (util.isNull(name)) {
                    this.optRole.errorMsg = '角色名称不能为空';
                    return;
                }
                if (util.isNull(description)) {
                    this.optRole.errorMsg = '描述不能为空';
                    return;
                }
                if (this.optRole.optType === 'edit' && util.isNull(id)) {
                    return;
                }
                let url = '';
                if (this.optRole.optType === 'add') {
                    url = CommonUrl.ROLE_MGR_ROLE_ADD_URL;
                } else if (this.optRole.optType === 'edit') {
                    url = CommonUrl.ROLE_MGR_ROLE_EDIT_URL;
                }
                let param = {
                    id: id,
                    name: name,
                    description: description
                };

                let vm = this;
                safeHttp.jwtPost(url, param, function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.refreshTable();
                        vm.editRoleModal = false;
                    } else {
                        vm.optRole.errorMsg = msg;
                    }
                }, vm);
            },
            onCancelRole () {
                this.optRole.errorMsg = '';
                this.optRole.optData = {
                    id: null,
                    name: '',
                    description: ''
                };
                this.editRoleModal = false;
            },
            assignPrivilege (role) {
                let name = role['name'];
                let roleId = role['id'];
                this.optPrivilege.roleId = roleId;
                this.optPrivilege.modalTitle = '分配权限(' + name + ')';
                let param = {
                    roleId: roleId
                };
                let vm = this;
                safeHttp.jwtGet(CommonUrl.ROLE_MGR_ROLE_PRIVILEGE_URL, param, function (data, status) {
                    let jsData = data;
                    if (jsData.code === 0) {
                        let rolePrivilege = jsData.data;
                        let tmpShowPrivilege = [];
                        for (let i = 0, maxI = vm.originPrivilegeData.length; i < maxI; i++) {
                            let oriParentItem = vm.originPrivilegeData[i];
                            const parentTitle = oriParentItem['title'];
                            let oriPageList = oriParentItem['children'];
                            let leafList = [];
                            for (let j = 0, maxJ = oriPageList.length; j < maxJ; j++) {
                                let pageItem = oriPageList[j];
                                const leafTitle = pageItem['title'];
                                const pageId = pageItem['pageId'];
                                let leafCheck = false;
                                const key = '' + pageId;
                                let leafExist = rolePrivilege[key];
                                if (!util.isNull(leafExist)) {
                                    leafCheck = true;
                                }
                                let leafItem = {
                                    title: leafTitle,
                                    pageId: pageId,
                                    checked: leafCheck
                                };
                                leafList.push(leafItem);
                            }
                            let parentItem = {
                                title: parentTitle,
                                expand: true,
                                children: leafList
                            };
                            tmpShowPrivilege.push(parentItem);
                        }
                        vm.showPrivilegeData = tmpShowPrivilege;
                        vm.editPrivilegeModal = true;
                    }
                }, vm);
            },
            cancelAssign () {
                this.editPrivilegeModal = false;
            },
            saveAssign () {
                let checkNodes = this.$refs.tree.getCheckedNodes();
                let pageIds = [];
                for (let i = 0, maxI = checkNodes.length; i < maxI; i++) {
                    let nodeItem = checkNodes[i];
                    let pageId = nodeItem['pageId'];
                    if (!util.isNull(pageId)) {
                        pageIds.push(pageId);
                    }
                }
                let param = {
                    roleId: this.optPrivilege.roleId,
                    pageList: pageIds
                };
                let vm = this;
                safeHttp.jwtPost(CommonUrl.ROLE_MGR_ROLE_PRIVILEGE_EDIT_URL, param, function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.editPrivilegeModal = false;
                    } else {
                        vm.optPrivilege.errorMsg = msg;
                    }
                }, vm);
            }
        }
    };
</script>
