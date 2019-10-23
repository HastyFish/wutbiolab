import React,{Component} from 'react';
import {
  Tabs,
  Form,
  Row,
  Col,
  Button,
  message
} from 'antd'

import BraftEditor from '@/components/rich-text-editor/rich-text-editor';
import {reqConDes,reqSaveConDes, reqPublishConDes} from '@/api';

const {TabPane} = Tabs
const {Item} = Form;

class ContactUs extends Component {
  constructor(props){
    super(props);
    // 创建用来保存ref标识的标签对象的容器
    this.editor = React.createRef()
    this.state = {
      title:'',
      context:null
    }
  }
  

  //保存或发布联系我们
  saveOrPublishData = async (type) => {
        const id = this.id || null;
        const categoryId = 13;
        const context = this.editor.current.getContext();
        const param = {id,categoryId, context}
        //判断是保存还是发布
        let result;
        if(type==='save'){
          //编辑状态下的保存
          param.publishStatus = 0;
          result = await reqSaveConDes(param);
        }else{
          //编辑状态下的发布
          param.publishStatus = 1;
          result = await reqPublishConDes(param);
        }
        if(result.code === 0){
          message.success(`${type === 'save'?'保存':'发布'}成功`);
        }else{
          message.error(`${type === 'save'?'保存':'发布'}失败，请稍后再试！`);
        }

        //获取联系我们
        const result1 = await reqConDes();
        if(result1.code === 0){
          let id = null,title = null ,context = null;
          result1.result && ({id,title,context} = result1.result)
          this.id = id;
          this.setState({
              title,
            context
          })
        }else{
          message.error('获取联系我们失败，请稍后再试')
        }
  }

  //取消编辑联系我们，重新获取原来的储存数据
  handleCancel = async () => {

  }

  async componentDidMount(){
    //获取联系我们
    const result = await reqConDes();
    if(result.code === 0){
      let id = null,title = null ,context = null;
      result.result && ({id,title,context} = result.result)
      this.id = id;
      this.setState({
        title,
        context
      })
    }else{
      message.error('获取联系我们失败，请稍后再试')
    }
  }

  render(){
    const {context} = this.state
    //const {getFieldDecorator} = this.props.form;

    return (
      <div className="lab">
        <div className="lab-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
              <TabPane tab="加入我们" key="/contact" >
              </TabPane>
            </Tabs>
        </div>
        <div className='lab-body'>
          <div className='lab-content'>
              <Form className="form-lable-info">
                <Item label='内容'>
                  {
                    context?<BraftEditor  ref={this.editor} context={context}></BraftEditor>:null
                  }

                  {
                    context === null ?<BraftEditor  ref={this.editor} context={''}></BraftEditor>:null
                  }
                  
                </Item>
                <Row type="flex" justify="end">
                    <Col>
                      <Button type='primary' style={{width:180,height:40,cursor:'pointer'}} onClick={() => this.saveOrPublishData('save')}>保存</Button>
                      <Button type='primary' style={{margin:'0 20px',width:180,height:40,cursor:'pointer'}} onClick={() => this.saveOrPublishData('publish')}>发布</Button>
                      <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.handleCancel}>取消</Button>
                    </Col>
                  </Row>
              </Form>
            </div>
        </div>
      </div>
      
    )
  }
}

export default Form.create()(ContactUs)