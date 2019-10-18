import React,{Component} from 'react';
import {Link, withRouter} from 'react-router-dom'
import {
  Menu, 
  Icon,
  Modal,
  Button 
} from 'antd';

import {logout} from "@/api"
import menuList from '@/config/menuConfig';
import storageUtils from '@/utils/storageUtils.js';

const SubMenu = Menu.SubMenu;

class LeftNav extends Component{
  
  //模态框初始不显示
  state = { visible: false };

  handleOk = async(e) => {
    this.setState({
      visible: false,
    });

    //模拟退出登录
    storageUtils.removeUser();
    this.props.history.replace('/login');

    //删除localStorage中保存的用户
    let data = await logout();
    if(data.code === 0){
     storageUtils.removeUser();
     storageUtils.removeToken();
     //跳转到login页面
     this.props.history.replace('/login');
    }
  };

  handleCancel = e => {
    this.setState({
      visible: false,
    });
  };

  //根据menu的数据数组生成对应的标签数组
  //1.map + 递归 实现
  getMenuNodes = (menuList) => {
    //得到当前请求的路由路径
    const path = this.props.location.pathname;
    let menuListOld = JSON.parse(JSON.stringify(menuList));

    return menuListOld.map(item => {
      if(!item.children){
        //判断是否为退出登录
        if(item.key === '/exit'){
          //给退出选项添加点击事件
          return (
            <Menu.Item key={item.key}>
              <Link to='/' onClick={this.logout}>
                <Icon type={item.icon}/>
                <span>{item.title}</span>
              </Link>
            </Menu.Item>
          )
        }else{
          //正常处理
          return (
            <Menu.Item key={item.key}>
              <Link to={item.key}>
                <Icon type={item.icon}/>
                <span>{item.title}</span>
              </Link>
            </Menu.Item>
          )
        }
        
      }else{
        //查找一个与当前请求路径匹配的子Item
        const cItem = item.children.find(cItem => path.indexOf(cItem.key)===0);
        //如果存在，说明当前item的子列表需要打开
        if(cItem){
          this.openKey = item.key;
        }

        return (
          <SubMenu
            key={item.key}
            title={
              <span>
                <Icon type={item.icon}/>
                <span>{item.title}</span>
              </span>
            }
          >
          {
            this.getMenuNodes(item.children)
          }
          </SubMenu>
        )
      }
    })
  }

  //2.reduce + 递归 实现
  getMenuNodesReduce = (menuList) => {
    return menuList.reduce((prev,item) => {
      //向prev添加<Menu.Item>
      if(!item.children){
        prev.push((
          <Menu.Item key={item.key}>
            <Link to={item.key}>
              <Icon type={item.icon}/>
              <span>{item.title}</span>
            </Link>
          </Menu.Item>
        ))
      }else{
        prev.push((
          <SubMenu
            key={item.key}
            title={
              <span>
                <Icon type={item.icon}/>
                <span>{item.title}</span>
              </span>
            }
          >
          {
            this.getMenuNodesReduce(item.children)
          }
          </SubMenu>
        ))
      }
      return prev;
    },[])
  }

  logout = () => {
    this.setState({
      visible:true
    })
    //alert('退出')
  }

  //在第一次render()之前执行一次
  //为第一个render()准备数据(必须是同步的)
  componentWillMount(){
    this.menuNodes = this.getMenuNodes(menuList)
  }

  render(){
    //得到当前请求的路由路径
    let path = this.props.location.pathname;
    if(path.indexOf('/home')===0 || path === '/'){
      //当前请求的是home,侧边显示的还是user
      path = '/home';
    }else if(path.indexOf('/laboratory')===0){
      //当前请求的是实验室简介或其子路由界面,侧边显示的还是laboratory
      path = '/laboratory';
    }else if(path.indexOf('/news')===0){
      //当前请求的是新闻编辑或其子路由界面,侧边显示的还是news
      path = '/news';
    // }else if(path.indexOf('/notice')===0){
    //   //当前请求的是通知总览或其子路由界面,侧边显示的还是notice
    //   path = '/notice';
    }else if(path.indexOf('/contact')===0){
      //当前请求的是联系我们或其子路由界面,侧边显示的还是notice
      path = '/contact';
    }else if(path.indexOf('/source')===0){
      //当前请求的是资源发布总览或其子路由界面,侧边显示的还是source
      path = '/source';
    }else if(path.indexOf('/science')===0){
      //当前请求的是科研工作或其子路由界面,侧边显示的还是source
      path = '/science';
    }

    const openKey = this.openKey;

    const {visible} = this.state;
    return (
      <div className="left-nav">
        <Menu
          mode="inline"
          selectedKeys={[path]}
          defaultOpenKeys = {[openKey]}
        >
          {
            this.menuNodes
          }
        </Menu>
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


/*
withRouter高阶组件：
包装非路由组件，返回一个新的组件
新的组件向非路由组件传递三个属性：history/match/location
*/
export default withRouter(LeftNav)