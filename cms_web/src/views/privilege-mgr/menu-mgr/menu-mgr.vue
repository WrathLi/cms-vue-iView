<style lang="less">
    @import './menu-mgr.less';
</style>

<template>
    <div class="menu-mgr">
        <div class="menu-mgr-body-con">
            <Row type="flex" justify="start">
                <Col span="5">
                    <Card style="width:240px;min-height:200px;">
                        <p slot="title">
                            目录
                        </p>
                        <a href="#" slot="extra" @click.prevent="refreshMenuOne">
                            <Icon type="ios-loop-strong"></Icon>
                            刷新
                        </a>
                        <RadioGroup v-model="menuOneIndex" vertical size="large" v-for="(item,index) in menuOneList" @on-change="menuOneChange()">
                            <Radio :label="index" style="width:220px;">
                                <span>{{item.title}}</span>
                            </Radio>
                        </RadioGroup>
                    </Card>
                </Col>
                <Col span="2">
                    <div class="menu_btn_group">
                        <Row>
                            <Button type="info" @click="onAddMenuOne">
                                新增目录
                            </Button>
                        </Row>
                        <br>
                        <Row>
                            <Button type="warning" @click="onEditMenuOne">
                                编辑目录
                            </Button>
                        </Row>
                        <br>
                        <Row>
                            <Button type="error" @click="openDeleteMenuOne">
                                删除目录
                            </Button>
                        </Row>
                    </div>
                </Col>

                <Col span="5" offset="3">
                <Card style="width:240px;min-height:200px;">
                    <p slot="title">
                        明细页
                    </p>
                    <a href="#" slot="extra" @click.prevent="refreshMenuPage">
                        <Icon type="ios-loop-strong"></Icon>
                        刷新
                    </a>
                    <RadioGroup v-model="menuPageIndex" vertical size="large" v-for="(item,index) in menuPageList">
                        <Radio :label="index" style="width:220px;">
                            <span>{{item.title}}</span>
                        </Radio>
                    </RadioGroup>
                </Card>
                </Col>
                <Col span="2">
                <div class="menu_btn_group">
                    <Row>
                        <Button type="info" @click="onAddMenuPage">
                            新增明细页
                        </Button>
                    </Row>
                    <br>
                    <Row>
                        <Button type="warning" @click="onEditMenuPage">
                            编辑明细页
                        </Button>
                    </Row>
                    <br>
                    <Row>
                        <Button type="error" @click="openDeleteMenuPage">
                            删除明细页
                        </Button>
                    </Row>
                </div>
                </Col>
            </Row>
        </div>
        <Modal :closable="false" :title="optMenuOne.modalTitle" v-model="editOneModal" class-name="edit_one_modal"
         scrollabel="true">
            <Row type="flex" justify="start">
                <Col span="3">
                    <div class="my_modal_tip_label">
                        <label>路径:</label>
                    </div>
                </Col>
                <Col span="8">
                    <Input v-model="optMenuOne.optData.routeName" placeholder="输入路径(route)" class="my_modal_input_large" size="large">

                    </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>标题:</label>
                </div>
                </Col>
                <Col span="8">
                    <Input v-model="optMenuOne.optData.title" placeholder="输入标题" class="my_modal_input_large" size="large">

                    </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>图标:</label>
                </div>
                </Col>
                <Col span="8">
                    <Input v-model="optMenuOne.optData.icon" placeholder="输入图标" class="my_modal_input_large" size="large">

                    </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>排序:</label>
                </div>
                </Col>
                <Col span="8">
                    <Input v-model="optMenuOne.optData.sortIndex" placeholder="0" class="my_modal_input_middle" size="large">

                    </Input>
                </Col>
            </Row>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                        <div class="my_error_tip">
                            <label>{{optMenuOne.errorMsg}}</label>
                        </div>
                    </Col>
                    <Col span="4">
                        <Button class="my_modal_btn" @click="onCancelMenuOne">
                            放弃
                        </Button>
                    </Col>
                    <Col span="4">
                        <Button type="primary" class="my_modal_btn" @click="onSaveMenuOne">
                            保存
                        </Button>
                    </Col>
                </Row>
            </div>
        </Modal>

        <Modal :closable="false" title="删除目录" v-model="deleteOneModal" class-name="delete_one_modal"
               scrollabel="true">
            <div class="delete_modal_content_label">
                <label>是否删除[{{optMenuOne.optData.title}}]这个目录?</label>
            </div>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optMenuOne.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="onCancelMenuOne">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="error" class="my_modal_btn" @click="deleteMenuOne">
                        删除
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>
        <Modal :closable="false" :title="optMenuPage.modalTitle" v-model="editPageModal" class-name="edit_page_modal"
               scrollabel="true">
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>目录:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optMenuPage.optData.oneName" class="my_modal_input_large" size="large" disabled>
                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>路径:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optMenuPage.optData.routeName" placeholder="输入路径(route)" class="my_modal_input_large" size="large">

                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>标题:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optMenuPage.optData.title" placeholder="输入标题" class="my_modal_input_large" size="large">

                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>图标:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optMenuPage.optData.icon" placeholder="输入图标" class="my_modal_input_large" size="large">

                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>排序:</label>
                </div>
                </Col>
                <Col span="8">
                <Input v-model="optMenuPage.optData.sortIndex" placeholder="0" class="my_modal_input_middle" size="large">

                </Input>
                </Col>
            </Row>
            <br>
            <Row type="flex" justify="start">
                <Col span="3">
                <div class="my_modal_tip_label">
                    <label>隐藏:</label>
                </div>
                </Col>
                <Col span="3">
                <div class="edit_page_model_check">
                    <Checkbox v-model="optMenuPage.optData.hidePage" size="large">
                    </Checkbox>
                </div>
                </Col>
            </Row>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optMenuPage.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="onCancelMenuPage">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="primary" class="my_modal_btn" @click="onSaveMenuPage">
                        保存
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>

        <Modal :closable="false" title="删除明细页" v-model="deletePageModal" class-name="delete_page_modal"
               scrollabel="true">
            <div class="delete_modal_content_label">
                <label>是否删除[{{optMenuPage.optData.title}}]这个明细页?</label>
            </div>
            <div slot="footer">
                <Row type="flex" justify="end">
                    <Col span="8" offset="8">
                    <div class="my_error_tip">
                        <label>{{optMenuPage.errorMsg}}</label>
                    </div>
                    </Col>
                    <Col span="4">
                    <Button class="my_modal_btn" @click="onCancelMenuPage">
                        放弃
                    </Button>
                    </Col>
                    <Col span="4">
                    <Button type="error" class="my_modal_btn" @click="deleteMenuPage">
                        删除
                    </Button>
                    </Col>
                </Row>
            </div>
        </Modal>
    </div>
</template>

<script>
    import util from '../../../libs/util';
    import CommonUrl from '../../common/common_url';
    import safeHttp from '../../../libs/safe_http';
    export default {
        data () {
            return {
                menuOneList: [],
                menuPageList: [],
                menuOneIndex: '',
                menuPageIndex: '',
                editOneModal: false,
                editPageModal: false,
                deleteOneModal: false,
                deletePageModal: false,
                optMenuOne: {
                    optType: 'add',
                    modalTitle: '',
                    errorMsg: '',
                    optData: {
                        id: null,
                        routeName: '',
                        title: '',
                        icon: 'ios-folder-outline',
                        sortIndex: 0
                    }
                },
                optMenuPage: {
                    optType: 'add',
                    modalTitle: '',
                    errorMsg: '',
                    optData: {
                        id: null,
                        oneId: 0,
                        oneName: '',
                        routeName: '',
                        title: '',
                        icon: 'document',
                        sortIndex: 0,
                        hidePage: false
                    }
                }
            };
        },
        create () {
            // this.init();
        },
        mounted () {
            this.init();
        },
        methods: {
            init () {
                this.getMenuOneList();
            },
            getMenuOneList () {
                let vm = this;
                safeHttp.jwtGet(CommonUrl.MENU_MGR_MENU_ONE_LIST_URL, {}, function (data, status) {
                    let jsData = data;
                    if (jsData.code >= 0) {
                        let tmpMenuOneList = jsData.data;
                        vm.menuOneList = tmpMenuOneList;
                    }
                }, vm);
            },
            onAddMenuOne () {
                this.optMenuOne.optType = 'add';
                this.optMenuOne.modalTitle = '新建目录';
                this.optMenuOne.errorMsg = '';
                this.optMenuOne.optData = {
                    id:null,
                    routeName: '',
                    title: '',
                    icon: 'ios-folder-outline',
                    sortIndex: 0
                };
                this.editOneModal = true;
            },
            onEditMenuOne () {
                if (util.isNull(this.menuOneIndex) || this.menuOneIndex === '') {
                    this.$Notice.open({
                        title: '请先选中目录项',
                        desc: ''
                    });
                } else {
                    let currentMenuOne = util.myCopyObj(this.menuOneList[this.menuOneIndex]);
                    let id = currentMenuOne['id'];
                    let routeName = currentMenuOne['routeName'];
                    let title = currentMenuOne['title'];
                    let icon = currentMenuOne['icon'];
                    let sortIndex = currentMenuOne['sortIndex'];
                    this.optMenuOne.optType = 'edit';
                    this.optMenuOne.modalTitle = '编辑目录';
                    this.optMenuOne.errorMsg = '';
                    this.optMenuOne.optData = {
                        id: id,
                        routeName: routeName,
                        title: title,
                        icon: icon,
                        sortIndex: sortIndex
                    };
                    this.editOneModal = true;
                }
            },
            openDeleteMenuOne () {
                if (util.isNull(this.menuOneIndex) || this.menuOneIndex === '') {
                    this.$Notice.open({
                        title: '请先选中目录项',
                        desc: ''
                    });
                } else {
                    let currentMenuOne = util.myCopyObj(this.menuOneList[this.menuOneIndex]);
                    let id = currentMenuOne['id'];
                    let routeName = currentMenuOne['routeName'];
                    let title = currentMenuOne['title'];
                    let icon = currentMenuOne['icon'];
                    let sortIndex = currentMenuOne['sortIndex'];
                    this.optMenuOne.optType = 'delete';
                    this.optMenuOne.modalTitle = '删除目录';
                    this.optMenuOne.errorMsg = '';
                    this.optMenuOne.optData = {
                        id: id,
                        routeName: routeName,
                        title: title,
                        icon: icon,
                        sortIndex: sortIndex
                    };
                    this.deleteOneModal = true;
                }
            },
            deleteMenuOne () {
                this.optMenuOne.errorMsg = '';
                let id = this.optMenuOne.optData['id'];
                if (util.isNull(id)) {
                    return;
                } else {
                    let param = {
                        id: id
                    };

                    let vm = this;
                    safeHttp.jwtPost(CommonUrl.MENU_MGR_MENU_ONE_DELETE_URL, param, function (data, status) {
                        let jsonObj = JSON.parse(data);
                        let code = jsonObj.code;
                        let msg = jsonObj.msg;
                        if (code >= 0) {
                            vm.refreshMenuOne();
                            vm.deleteOneModal = false;
                        } else {
                            vm.optMenuOne.errorMsg = msg;
                        }
                    }, vm);
                }
            },
            onSaveMenuOne () {
                this.optMenuOne.errorMsg = '';
                let id = this.optMenuOne.optData['id'];
                let routeName = this.optMenuOne.optData['routeName'];
                let title = this.optMenuOne.optData['title'];
                let icon = this.optMenuOne.optData['icon'];
                let sortIndex = this.optMenuOne.optData['sortIndex'];
                if (util.isNull(routeName)) {
                    this.optMenuOne.errorMsg = '路径不能为空';
                    return;
                }
                if (util.isNull(title)) {
                    this.optMenuOne.errorMsg = '标题不能为空';
                    return;
                }
                if (util.isNull(icon)) {
                    this.optMenuOne.errorMsg = '图标不能为空';
                    return;
                }

                if (util.isNull(sortIndex)) {
                    this.optMenuOne.errorMsg = '请输入排序';
                    return;
                }
                if (this.optMenuOne.optType === 'edit' && util.isNull(id)) {
                    return;
                }
                let url = '';
                if (this.optMenuOne.optType === 'add') {
                    url = CommonUrl.MENU_MGR_MENU_ONE_ADD_URL;
                } else if (this.optMenuOne.optType === 'edit') {
                    url = CommonUrl.MENU_MGR_MENU_ONE_EDIT_URL;
                }
                let param = {
                    id: id,
                    routeName: routeName,
                    title: title,
                    icon: icon,
                    sortIndex: sortIndex
                };

                let vm = this;
                safeHttp.jwtPost(url, param, function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.refreshMenuOne();
                        vm.editOneModal = false;
                    } else {
                        vm.optMenuOne.errorMsg = msg;
                    }
                }, vm);
            },
            onCancelMenuOne () {
                this.optMenuOne.errorMsg = '';
                this.optMenuOne.optData = {
                    routeName: '',
                    title: '',
                    icon: 'ios-folder-outline',
                    sortIndex: 0
                };
                this.editOneModal = false;
            },
            menuOneChange () {
                this.menuPageIndex = '';
                this.menuPageList = [];
                this.getMenuPageList();
            },
            refreshMenuOne () {
                this.menuOneIndex = '';
                this.menuPageIndex = '';
                this.menuPageList = [];
                this.getMenuOneList();
            },
            refreshMenuPage () {
                if (util.isNull(this.menuOneIndex) || this.menuOneIndex === '') {
                    this.$Notice.open({
                        title: '请先选中目录项',
                        desc: ''
                    });
                } else {
                    this.menuPageIndex = '';
                    this.menuPageList = [];
                    this.getMenuPageList();
                }
            },
            getMenuPageList () {
                let vm = this;
                let currentMenuOne = this.menuOneList[this.menuOneIndex];
                const menuOneId = currentMenuOne['id'];
                let param = {
                    oneId: menuOneId
                };
                safeHttp.jwtGet(CommonUrl.MENU_MGR_MENU_PAGE_LIST_URL, param, function (data, status) {
                    let jsData = data;
                    if (jsData.code >= 0) {
                        let tmpMenuPageList = jsData.data;
                        vm.menuPageList = tmpMenuPageList;
                    }
                }, vm);
            },
            onAddMenuPage () {
                if (util.isNull(this.menuOneIndex) || this.menuOneIndex === '') {
                    this.$Notice.open({
                        title: '请先选中目录项',
                        desc: ''
                    });
                } else {
                    let currentMenuOne = this.menuOneList[this.menuOneIndex];
                    const menuOneId = currentMenuOne['id'];
                    let oneName = currentMenuOne['title'];
                    this.optMenuPage.optType = 'add';
                    this.optMenuPage.modalTitle = '新建明细页';
                    this.optMenuPage.errorMsg = '';
                    this.optMenuPage.optData = {
                        id: null,
                        oneId: menuOneId,
                        oneName: oneName,
                        routeName: '',
                        title: '',
                        icon: 'document',
                        sortIndex: 0,
                        hidePage: false
                    };
                    this.editPageModal = true;
                }
            },
            onEditMenuPage () {
                if (util.isNull(this.menuPageIndex) || this.menuPageIndex === '') {
                    this.$Notice.open({
                        title: '请先选中明细页项',
                        desc: ''
                    });
                } else {
                    let currentMenuOne = this.menuOneList[this.menuOneIndex];
                    let currentMenuPage = util.myCopyObj(this.menuPageList[this.menuPageIndex]);
                    let id = currentMenuPage['id'];
                    let oneId = currentMenuPage['oneId'];
                    let oneName = currentMenuOne['title'];
                    let routeName = currentMenuPage['routeName'];
                    let title = currentMenuPage['title'];
                    let icon = currentMenuPage['icon'];
                    let sortIndex = currentMenuPage['sortIndex'];
                    let hidePage = currentMenuPage['hidePage'];
                    this.optMenuPage.optType = 'edit';
                    this.optMenuPage.modalTitle = '编辑明细页';
                    this.optMenuPage.errorMsg = '';
                    this.optMenuPage.optData = {
                        id: id,
                        oneId: oneId,
                        oneName: oneName,
                        routeName: routeName,
                        title: title,
                        icon: icon,
                        sortIndex: sortIndex,
                        hidePage: hidePage
                    };
                    this.editPageModal = true;
                }
            },
            openDeleteMenuPage () {
                if (util.isNull(this.menuPageIndex) || this.menuPageIndex === '') {
                    this.$Notice.open({
                        title: '请先选中明细页',
                        desc: ''
                    });
                } else {
                    let currentMenuPage = util.myCopyObj(this.menuPageList[this.menuPageIndex]);
                    let id = currentMenuPage['id'];
                    let routeName = currentMenuPage['routeName'];
                    let title = currentMenuPage['title'];
                    let icon = currentMenuPage['icon'];
                    let sortIndex = currentMenuPage['sortIndex'];
                    this.optMenuPage.optType = 'delete';
                    this.optMenuPage.modalTitle = '删除明细页';
                    this.optMenuPage.errorMsg = '';
                    this.optMenuPage.optData = {
                        id: id,
                        routeName: routeName,
                        title: title,
                        icon: icon,
                        sortIndex: sortIndex
                    };
                    this.deletePageModal = true;
                }
            },
            deleteMenuPage () {
                this.optMenuPage.errorMsg = '';
                let id = this.optMenuPage.optData['id'];
                if (util.isNull(id)) {
                    return;
                } else {
                    let param = {
                        id: id
                    };

                    let vm = this;
                    safeHttp.jwtPost(CommonUrl.MENU_MGR_MENU_PAGE_DELETE_URL, param, function (data, status) {
                        let jsonObj = JSON.parse(data);
                        let code = jsonObj.code;
                        let msg = jsonObj.msg;
                        if (code >= 0) {
                            vm.refreshMenuPage();
                            vm.deletePageModal = false;
                        } else {
                            vm.optMenuPage.errorMsg = msg;
                        }
                    }, vm);
                }
            },
            onSaveMenuPage () {
                this.optMenuPage.errorMsg = '';
                let id = this.optMenuPage.optData['id'];
                let oneId = this.optMenuPage.optData['oneId'];
                let routeName = this.optMenuPage.optData['routeName'];
                let title = this.optMenuPage.optData['title'];
                let icon = this.optMenuPage.optData['icon'];
                let sortIndex = this.optMenuPage.optData['sortIndex'];
                let hidePage = this.optMenuPage.optData['hidePage'];
                if (util.isNull(routeName)) {
                    this.optMenuPage.errorMsg = '路径不能为空';
                    return;
                }
                if (util.isNull(title)) {
                    this.optMenuPage.errorMsg = '标题不能为空';
                    return;
                }
                if (util.isNull(icon)) {
                    this.optMenuPage.errorMsg = '图标不能为空';
                    return;
                }

                if (util.isNull(sortIndex)) {
                    this.optMenuPage.errorMsg = '请输入排序';
                    return;
                }
                if (this.optMenuPage.optType === 'edit' && util.isNull(id)) {
                    return;
                }
                let url = '';
                if (this.optMenuPage.optType === 'add') {
                    url = CommonUrl.MENU_MGR_MENU_PAGE_ADD_URL;
                } else if (this.optMenuPage.optType === 'edit') {
                    url = CommonUrl.MENU_MGR_MENU_PAGE_EDIT_URL;
                }
                let param = {
                    id: id,
                    oneId: oneId,
                    routeName: routeName,
                    title: title,
                    icon: icon,
                    sortIndex: sortIndex,
                    hidePage: hidePage
                };

                let vm = this;
                safeHttp.jwtPost(url, param, function (data, status) {
                    let jsonObj = JSON.parse(data);
                    let code = jsonObj.code;
                    let msg = jsonObj.msg;
                    if (code >= 0) {
                        vm.refreshMenuPage();
                        vm.editPageModal = false;
                    } else {
                        vm.optMenuPage.errorMsg = msg;
                    }
                }, vm);
            },
            onCancelMenuPage () {
                this.optMenuPage.errorMsg = '';
                this.optMenuPage.optData = {
                    id: null,
                    oneId: null,
                    oneName: '',
                    routeName: '',
                    title: '',
                    icon: 'ios-folder-outline',
                    sortIndex: 0,
                    hidePage: false
                };
                this.editPageModal = false;
            }
        }
    };
</script>
