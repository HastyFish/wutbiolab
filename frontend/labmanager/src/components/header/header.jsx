import React,{Component} from 'react';
import { Modal, Button, Menu, Dropdown, } from 'antd';
import {Link,withRouter} from 'react-router-dom';

import storageUtils from '@/utils/storageUtils.js';
import Logo from '@/assets/images/logo.png';
import './index.less';
import {logout} from "@api/index"
class MyHeader extends Component{
  constructor(props){
    super(props);
    const {username} = storageUtils.getUser();
    this.state = {
      username,
      visible: false
    }
  }


  handleOk = async(e) => {
    this.setState({
      visible: false,
    });
    //删除localStorage中保存的用户
   let data = await logout();
   if(data.code === 0){
    storageUtils.removeUser();
    //storageUtils.removeToken();
    //跳转到login页面
    this.props.history.replace('/login');
   }
  };

  handleCancel = e => {
    this.setState({
      visible: false,
    });
  };

  logout = () => {
    this.setState({
      visible:true
    })
    //alert('退出')
  }

  render(){
    const {username,visible} = this.state;
    const menu = (
      <Menu>
        <Menu.Item>
          <Link to="/forget">
           修改密码
          </Link>
        </Menu.Item>
      </Menu>
    );
    return (
      <div className='headerBox'>
        <img src={Logo} alt='logo' />
        <div className='headerTitle'>
          实验室后台管理中心
        </div>
        <span className='usermes'>
        <Dropdown overlay={menu}>
           <span>用户, {username}&nbsp;&nbsp;&nbsp;</span> 
        </Dropdown>
          [<span className='linktext' onClick={this.logout}>退出</span>]
        </span>
        <Modal
          visible={visible}
          title="提示"
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          footer={[
            <Button key="back" onClick={this.handleCancel}>
              取消
            </Button>,
            <Button key="submit" type="primary" onClick={this.handleOk}>
              确定
            </Button>,
          ]}
        >
          <p>是否退出登录?</p>
        </Modal>
      </div>
    )
  }
} 


export default withRouter(MyHeader)