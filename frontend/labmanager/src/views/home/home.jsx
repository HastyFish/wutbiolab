import React, {Component} from 'react';
import {
  Tabs,
} from 'antd';

// import {reqHomePublish} from '../../api';
import storageUtils from '@/utils/storageUtils';

import PictureUpload from './pictureUpload/pictureUpload';
import FriendLink from './friendLink/friendLink';
import Footer from './footer/footer';

import './home.less';

const {TabPane} = Tabs

class Home extends Component{

  publish = async () => {
    // const result = await reqHomePublish();
    // if(result.code === 0){
    //   message.success('发布成功');
    // }else{
    //   message.error('发布失败，请稍后再试');
    // }
  }

  componentWillMount(){
    const user = storageUtils.getUser() || {};
    if(!user || !user.username){
      //自动跳转到登陆
      this.props.history.replace('/login')
    }
  }

  render(){
    return (
      <div className='home'>
        <div className="home-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname}>
            <TabPane tab="首页设置" key="/home">
            </TabPane>
          </Tabs>
        </div>
        <div className='home-body'>
          <div className='home-content'>
            <PictureUpload />
            <FriendLink />
            <Footer />
          </div>
        </div>
      </div>
    )
  }
}

export default Home