import React,{Component} from 'react';
import {
  Row,
  Col,
  Input,
  Icon,
  Button
} from 'antd';

class Footer extends Component{

  state = {
    footerlist:[]
  }

  //修改页脚设置中某一项
changeText = (index, e) => {
  //更新对应index下的描述
  const footerlist = this.state.footerlist;
  footerlist[index] = e.target.value;
  this.setState(state => (
    {footerlist}
  ));
}

  //删除页脚设置中的某一项
  deleteItem = (index) => {
    const footerlist = this.state.footerlist;
    footerlist.splice(index, 1);
    this.setState(state => (
      {footerlist}
    ));
  }

  //新增页脚设置中的某一项
  addFooterList = () => {
    const item = ''
    this.setState(state => (
      {footerlist:[...state.footerlist, item]}
    ));
  }

  render(){
    const {footerlist} = this.state;
    return (
      <div>
        <p className="subtitle">页脚设置</p>
        <div className="subcontent">
          {
            footerlist.map((item,index) => (
              <Row type='flex' justify='start' align='middle' key={index} style={{marginBottom:20}}>
                <Col span={4}>
                  输入框{index + 1}
                </Col>
                <Col span={8} offset={2}>
                  <Input value={item} onChange={this.changeText.bind(this,index)} placeholder='请输入页脚'/>
                </Col>
                <Col span={1} offset={2}>
                  <Icon type='delete' style={{cursor:'pointer'}} onClick={this.deleteItem.bind(this,index)}/>
                </Col>
              </Row>
            ))
          }
          {
            footerlist.length < 5 ?  (
            <Row type='flex' justify='center' style={{margin:'40px 0'}}>
              <Col>
                <Icon type='plus' style={{fontSize:25}} onClick={this.addFooterList}/>
              </Col>
            </Row>
            ) : null
          }
          
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

export default Footer;