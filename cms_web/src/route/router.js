import Main from '@/views/main/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login/login.vue')
};

export const registerRouter = {
    path: '/register',
    name: 'register',
    meta: {
        title: 'Register - 注册'
    },
    component: () => import('@/views/register/register.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const preview = {
    path: '/preview',
    name: 'preview',
    component: () => import('@/views/form/article-publish/preview.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        { path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') },
        { path: 'ownspace', title: '个人中心', name: 'ownspace_index', component: () => import('@/views/own-space/own-space.vue') }
        // { path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/privilege-mgr',
        name: 'privilege-mgr',
        title: '权限管理',
        component: Main,
        children: [
            {
                path: 'menu-mgr',
                name: 'menu-mgr',
                title: '菜单页面',
                component: () => import('@/views/privilege-mgr/menu-mgr/menu-mgr.vue')
            },
            {
                path: 'role-mgr',
                name: 'role-mgr',
                title: '角色管理',
                component: () => import('@/views/privilege-mgr/role-mgr/role-mgr.vue')
            },
            {
                path: 'role-privilege',
                name: 'role-privilege',
                title: '角色权限',
                component: () => import('@/views/privilege-mgr/role-privilege/role-privilege.vue')
            },
            {
                path: 'user-mgr',
                name: 'user-mgr',
                title: '用户管理',
                component: () => import('@/views/privilege-mgr/user-mgr/user-mgr.vue')
            }
        ]
    },
    {
        path: '/test_menu',
        name: 'test_menu',
        title: '测试目录',
        component: Main,
        children: [
            {
                path: 'test_page_1',
                name: 'test_page_1',
                title: '测试明细页',
                component: () => import('@/views/test_menu/test_page.vue')
            }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    preview,
    locking,
    ...appRouter,
    page500,
    page403,
    page404,
    registerRouter
];

