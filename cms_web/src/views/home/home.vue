<style lang="less">
    @import "./home.less";
    @import "../../styles/common.less";
</style>
<template>
    <div class="home-main">
        <Row :gutter="10">
            <Col :md="24" :lg="8">
            <Row class-name="home-page-row1" :gutter="10">
                <Col :md="12" :lg="24" :style="{marginBottom: '10px'}">
                <Card>
                    <Row type="flex" class="user-infor">
                        <Col span="8">
                        <Row class-name="made-child-con-middle" type="flex" align="middle">
                            <img class="avator-img" :src="avatorPath" />
                        </Row>
                        </Col>
                        <Col span="16" style="padding-left:6px;">
                        <Row class-name="made-child-con-middle" type="flex" align="middle">
                            <div>
                                <b class="card-user-infor-name">{{userName}}</b>
                                <p>现场喷口水</p>
                            </div>
                        </Row>
                        </Col>
                    </Row>
                    <div class="line-gray"></div>
                    <Row class="margin-top-8">
                        <Col span="8"><p class="notwrap">上次登录时间:</p></Col>
                        <Col span="16" class="padding-left-8">2018.03.12-13:32:20</Col>
                    </Row>
                    <Row class="margin-top-8">
                        <Col span="8"><p class="notwrap">上次登录地点:</p></Col>
                        <Col span="16" class="padding-left-8">福州</Col>
                    </Row>
                </Card>
                </Col>
            </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import util from '../../libs/util';

    export default {
        name: 'home',
        data () {
            return {
                userName: ''
            };
        },
        created () {
            this.init();
        },
        computed: {
            avatorPath () {
                return localStorage.avatorImgPath;
            }
        },
        methods: {
            init () {
                const nickname = Cookies.get('nickname');
                const userId = Cookies.get('user');
                this.userName = nickname;
                if (util.isNull(this.userName)) {
                    this.userName = userId;
                }
            }
        }
    };
</script>
