import React,{Component} from 'react';
import {
  Row,
  Col,
  Input,
  Icon,
  Button,
  message
} from 'antd';

import {reqCooplink, reqSendCooplink, reqDeleteCooplink} from '@/api';
class FriendLink extends Component {

  state = {
    friendLinks:[],
  }

//修改友情链接中名称和链接
changeText = (index, type, e) => {
  if(type === 'name'){
    //更新对应index下的标题
    const {friendLinks} = this.state;
    friendLinks[index].publishStatus = 0;
    friendLinks[index].context.name = e.target.value;
    this.setState({
      friendLinks
    });
  }else{
    //更新对应index下的描述
    const {friendLinks} = this.state;
    friendLinks[index].publishStatus = 0;
    friendLinks[index].context.url = e.target.value;
    this.setState({
      friendLinks
    });
  }
}

  //删除友情链接中的某一项
  deleteItem = async (index) => {
    const {friendLinks} = this.state;
    const {id} = friendLinks[index];
    const result = await reqDeleteCooplink(id);
    if(result.code === 0){
      message.error('删除友情链接成功');
      friendLinks.splice(index, 1);
      this.setState(state => (
        {friendLinks}
      ));
    }else{
      message.error('删除友情链接失败，请稍后再试')
    }
    
  }

  //新增友情链接中的某一项
  addFriendList = () => {
    const item = {
      id:null,
      context: {
        name:'',
        url:'',
      },
      publishStatus:null
    }
    this.setState(state => (
      {friendLinks:[...state.friendLinks, item]}
    ));
    
  }

  //保存或发布
  saveOrPublishData = async (type) => {
    //根据type来调整需要发布的数据
    const {friendLinks} = this.state;
    let params;
    if(type === 'save'){
      params = friendLinks.map(item => {
        var item1 = {...item};
        item1.context = {...item.context};
        item1.publishStatus === null && (item1.publishStatus = 0);
        item1.context = JSON.stringify(item1.context);
        return item1
      });
    }else{
      params = friendLinks.map(item => {
        var item1 = {...item};
        item1.context = {...item.context};
        item1.publishStatus = 1;
        item1.context = JSON.stringify(item.context);
        return item1
      });
    }
    //发送请求
    const result = await reqSendCooplink(params)
    if(result.code === 0){
      message.success(`${type === 'save'?'保存':'发布'}成功`);
      //重新获取数据
       //发送获取友情链接请求
      const result = await reqCooplink();
      if(result.code === 0){
        const friendLinks = result.result;
        friendLinks.forEach(item => {
          item.context = JSON.parse(item.context)
        })
        this.setState({friendLinks})
      }else{
        message.error('获取友情链接失败, 请稍后再试')
      }
    }else{
      message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
    }
  }

  async componentDidMount(){
    //发送获取友情链接请求
    const result = await reqCooplink();
    if(result.code === 0){
      const friendLinks = result.result;
      friendLinks.forEach(item => {
        item.context = JSON.parse(item.context)
      })
      this.setState({friendLinks})
    }else{
      message.error('获取友情链接失败, 请稍后再试')
    }
  }

  render(){
    const {friendLinks} = this.state;
    return (
      <div>
        <p className="subtitle">友情链接</p>
        <div className="subcontent">
          {
            friendLinks.map((item,index) => (
              <Row type='flex' justify='start' align='middle' key={index} style={{marginBottom:20}}>
                <Col span={4}>
                  <Input value={item.context.name} onChange={this.changeText.bind(this,index,'name')} placeholder='请输入产品名称'/>
                </Col>
                <Col span={8} offset={2}>
                  <Input value={item.context.url} onChange={this.changeText.bind(this,index,'url')} placeholder='请输入链接'/>
                </Col>
                <Col span={1} offset={2}>
                  <Icon type='delete' style={{cursor:'pointer'}} onClick={this.deleteItem.bind(this,index)}/>
                </Col>
              </Row>
            ))
          }
          <Row type='flex' justify='center' style={{margin:'40px 0'}}>
            <Col>
              <Icon type='plus' style={{fontSize:25}} onClick={this.addFriendList}/>
            </Col>
          </Row>
          <Row type="flex" justify="end">
            <Col>
              <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={() => this.saveOrPublishData('save')}>保存</Button>
              <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={() => this.saveOrPublishData('publish')}>发布</Button>
              <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.uploadImg}>取消</Button>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default FriendLink;