import React,{Component} from 'react';
import {
  Row,
  Col,
  Input,
  Icon,
  Button,
  message
} from 'antd';

import {reqFooter, reqDeleteFooter, reqSendFooter} from '@/api';
class Footer extends Component{

  state = {
    footerlist:[
    ]
  }

  //修改页脚设置中某一项
  changeText = (index, e) => {
    //更新对应index下的描述
    const {footerlist} = this.state;
    footerlist[index].publishStatus = 0;
    footerlist[index].context.context = e.target.value;
    this.setState({
      footerlist
    });
  }

  //删除页脚设置中的某一项
  deleteItem = async (index) => {
    const {footerlist} = this.state;
    const {id} = footerlist[index];
    const result = await reqDeleteFooter(id);
    if(result.code === 0){
      message.error('删除页脚成功');
      footerlist.splice(index, 1);
      this.setState(state => (
        {footerlist}
      ));
    }else{
      message.error('删除页脚失败，请稍后再试')
    }
  }

  //新增页脚设置中的某一项
  addFooterList = () => {
    const item = {
      id:null,
      context:{
        context:''
      },
      publishStatus:null
    }
    this.setState(state => (
      {footerlist:[...state.footerlist, item]}
    ));
  }

  //保存或发布
  saveOrPublishData = async (type) => {
    //根据type来调整需要发布的数据
    const {footerlist} = this.state;
    let params;
    if(type === 'save'){
      params = footerlist.map(item => {
        var item1 = {...item};
        item1.context = {...item.context};
        item1.publishStatus === null && (item1.publishStatus = 0);
        item1.context = JSON.stringify(item1.context);
        return item1
      });
    }else{
      params = footerlist.map(item => {
        var item1 = {...item};
        item1.context = {...item.context};
        item1.publishStatus = 1;
        item1.context = JSON.stringify(item.context);
        return item1
      });
    }
    //发送请求
    const result = await reqSendFooter(params)
    if(result.code === 0){
      message.success(`${type === 'save'?'保存':'发布'}成功`);
      //重新获取数据
       //发送获取友情链接请求
      const result = await reqFooter();
      if(result.code === 0){
        const footerlist = result.result;
        footerlist.forEach(item => {
          item.context = JSON.parse(item.context)
        })
        this.setState({footerlist})
      }else{
        message.error('获取页脚失败, 请稍后再试')
      }
    }else{
      message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
    }
  }


  async componentDidMount(){
    //发送获取友情链接请求
    const result = await reqFooter();
    if(result.code === 0){
      const footerlist = result.result;
      footerlist.forEach(item => {
        item.context = JSON.parse(item.context)
      })
      this.setState({footerlist})
    }else{
      message.error('获取页脚失败, 请稍后再试')
    }
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
                  <Input value={item.context.context} onChange={this.changeText.bind(this,index)} placeholder='请输入页脚'/>
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

export default Footer;