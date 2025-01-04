import { createRouter, createWebHistory } from "vue-router";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/homeViews.vue'),
            alias : '/inicio',
        },
        {
            path: '/tipoUsuario',
            name: 'tipoUsuario',
            component: () => import('../views/userTypeViews.vue'),
        },
        //Seccion menus
        {
            path:'/menuAnon',
            name: 'menuAnon',
            component: () => import('../views/menuAnonViews.vue'),
        },
        {
            path:'/menuClient',
            name: 'menuClient',
            component: () => import('../views/clientViews.vue'),
        },
        {
            path:'/menuAdmin',
            name: 'menuAdmin',
            component: () => import('../views/adminViews.vue'),
        },
        {
            path:'/menuDeveloper',
            name: 'menuDeveloper',
            compoent: () => import('../views/developerViews.vue'),
        },
        {
            path:'/menuEmployee',
            name: 'menuEmployee',
            component: () => import('../views/employeeViews.vue'),
        },
        //Seccion Anonimo, Cliente, Admin, Empleado
        {
            path:'/availableCars',
            name: 'availableCars',
            component: () => import('../views/availableCarsViews.vue'),
        },
        //Seccion Cliente, Admin, Desarrollador y Empleado
        {
            path:'/userSelfEdit',
            name: 'userSelfEdit',
            component: () => import('../views/userSelfEditViews.vue'),
        },
        //Seccion menu Cliente
        {
            path:'/rentCar',
            name: 'rentCar',
            component: () => import('../views/userSelfEditViews.vue'),
        },
        {
            path:'/purchaseHistory',
            name: 'purchaseHistory',
            component: () => import('../views/purchaseHistoryViews.vue'),
        },
        //Seccion menu admin
        {
            path:'/records',
            name: 'records',
            component: () => import('../views/recordsViews.vue'),
        },
        {
            path:'/adminAddUser',
            name: 'adminAddUser',
            component: () => import('../views/adminAddUserViews.vue'),
        },
        {
            path:'/adminEditUser',
            name: 'adminEditUser',
            component: () => import('../views/adminEditUserViews.vue'),
        },
        {
            path:'/adminRemoveUser',
            name: 'adminRemoveUser',
            component: () => import('../views/adminRemoveUserViews.vue'),
        },
        {
            path:'/adminAddCar',
            name: 'adminAddCar',
            component: () => import('../views/adminAddCarViews.vue'),
        },
        {
            path:'/adminEditCar',
            name: 'adminEditCar',
            component: () => import('../views/adminEditCarViews.vue'),
        },
        {
            path:'/adminRemoveCar',
            name: 'adminRemoveCar',
            component: () => import('../views/adminRemoveCarViews.vue'),
        },
        {
            path:'/adminAddBranch',
            name: 'adminAddBranch',
            component: () => import('../views/adminAddBranchViews.vue'),
        },
        {
            path:'/adminEditBranch',
            name: 'adminEditBranch',
            component: () => import('../views/adminEditBranchViews.vue'),
        },
        {
            path:'/adminRemoveBranch',
            name: 'adminRemoveBranch',
            component: () => import('../views/adminRemoveBranchViews.vue'),
        },
        //Seccion Desarrollador
        //Seccion Empleado
        {
            path:'/editDelivery',
            name: 'editDelivery',
            component: () => import('../views/editDeliveryViews.vue'),
        },
        {
            path:'/editAvailabilty',
            name: 'editAvailabilty',
            component: () => import('../views/editAvailabiltyViews.vue'),
        },
        
        

        /*{
            path:'/flota',
            name: 'flota',
            component: () => import('../views/flotaPage.vue'),
        },*/
        
      
    ],
});

export default router;