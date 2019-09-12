import React,{Component} from 'react';
import {
  Row,
  Col,
  Input,
  Icon,
  Button
} from 'antd';

class FriendLink extends Component {

  state = {
    friendLinks:[{
      id:null,
      name:'',
      link:'',
    }],
  }

//修改友情链接中名称和链接
changeText = (index, type, e) => {
  if(type === 'name'){
    //更新对应index下的标题
    const friendLinks = this.state.friendLinks;
    friendLinks[index].name = e.target.value;
    this.setState(state => (
      {friendLinks}
    ));
  }else{
    //更新对应index下的描述
    const friendLinks = this.state.friendLinks;
    friendLinks[index].link = e.target.value;
    this.setState(state => (
      {friendLinks}
    ));
  }
}

  //删除友情链接中的某一项
  deleteItem = (index) => {
    const friendLinks = this.state.friendLinks;
    friendLinks.splice(index, 1);
    this.setState(state => (
      {friendLinks}
    ));
  }

  //新增友情链接中的某一项
  addFriendList = () => {
    const item = {
      id:null,
      name:'',
      link:'',
    }
    this.setState(state => (
      {friendLinks:[...state.friendLinks, item]}
    ));
  }

  componentDidMount(){
    //发送获取友情链接请求
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
                  <Input value={item.name} onChange={this.changeText.bind(this,index,'name')} placeholder='请输入产品名称'/>
                </Col>
                <Col span={8} offset={2}>
                  <Input value={item.link} onChange={this.changeText.bind(this,index,'link')} placeholder='请输入链接'/>
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
              <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>保存</Button>
              <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.uploadImg}>发布</Button>
              <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.uploadImg}>取消</Button>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default FriendLink;