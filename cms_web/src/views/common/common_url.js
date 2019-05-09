
const BASE_HOST = 'http://127.0.0.1:8090';
// const BASE_HOST = 'http://192.168.2.227:9090';

const CommonUrl = {
    PUBLIC_KEY: BASE_HOST + '/cms-data/public_key',
    LOGIN_URL: BASE_HOST + '/cms-data/login',
    MENU_MGR_MENU_ONE_LIST_URL: BASE_HOST + '/cms-data/menu/one_list',
    MENU_MGR_MENU_ONE_ADD_URL: BASE_HOST + '/cms-data/menu/add_one',
    MENU_MGR_MENU_ONE_EDIT_URL: BASE_HOST + '/cms-data/menu/edit_one',
    MENU_MGR_MENU_ONE_DELETE_URL: BASE_HOST + '/cms-data/menu/delete_one',
    MENU_MGR_MENU_PAGE_LIST_URL: BASE_HOST + '/cms-data/menu/page_list',
    MENU_MGR_MENU_PAGE_ADD_URL: BASE_HOST + '/cms-data/menu/add_page',
    MENU_MGR_MENU_PAGE_EDIT_URL: BASE_HOST + '/cms-data/menu/edit_page',
    MENU_MGR_MENU_PAGE_DELETE_URL: BASE_HOST + '/cms-data/menu/delete_page',
    ROLE_MGR_ROLE_LIST_URL: BASE_HOST + '/cms-data/role/role_list',
    ROLE_MGR_ROLE_ADD_URL: BASE_HOST + '/cms-data/role/add_role',
    ROLE_MGR_ROLE_EDIT_URL: BASE_HOST + '/cms-data/role/edit_role',
    ROLE_MGR_ROLE_DELETE_URL: BASE_HOST + '/cms-data/role/delete_role',
    ROLE_MGR_PRIVILEGE_TREE_URL: BASE_HOST + '/cms-data/role/privilege_tree',
    ROLE_MGR_ROLE_PRIVILEGE_URL: BASE_HOST + '/cms-data/role/role_privilege',
    ROLE_MGR_ROLE_PRIVILEGE_EDIT_URL: BASE_HOST + '/cms-data/role/edit_privilege',
    USER_MGR_ROLE_LIST_URL: BASE_HOST + '/cms-data/user/filter_role',
    USER_MGR_USER_LIST_URL: BASE_HOST + '/cms-data/user/user_list',
    USER_MGR_USER_ADD_URL: BASE_HOST + '/cms-data/user/add_user',
    USER_MGR_USER_EDIT_URL: BASE_HOST + '/cms-data/user/edit_user',
    USER_MGR_USER_DELETE_URL: BASE_HOST + '/cms-data/user/delete_user',
    REGISTER_URL: BASE_HOST + '/cms-data/register',
    PERSON_GET_INFO_URL: BASE_HOST + '/cms-data/person/user_info',
    PERSON_EDIT_INFO_URL: BASE_HOST + '/cms-data/person/edit_info',
    PERSON_CHANGE_PWD_URL: BASE_HOST + '/cms-data/person/change_pwd'
};

export default CommonUrl;
