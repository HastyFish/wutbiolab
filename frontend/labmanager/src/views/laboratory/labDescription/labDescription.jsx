import React,{Component} from 'react';
import {
  Tabs,
  Form,
  Row,
  Col,
  Input,
  Button
} from 'antd'

import BraftEditor from '@/components/rich-text-editor/rich-text-editor';

const {TabPane} = Tabs
const {Item} = Form;

class LabDescription extends Component {
  state = {
    title:'',
    description:null
  }


  saveData = () => {
    this.props.form.validateFields(async (err, values) => {
      if(!err){
        //const id = this.id || null;
       // const {title, description} = values;
      }
    })
  }

  publishData = () => {
    this.props.form.validateFields(async (err, values) => {
      if(!err){
        //const id = this.id || null;
        //const {title, description} = values;
      }
    })
  }

  render(){
    const {title,description} = this.state
    const {getFieldDecorator} = this.props.form;

    return (
      <div className="lab">
        <div className="lab-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
              <TabPane tab="机构概况" key="/laboratory" >
              </TabPane>
              <TabPane tab="研究方向" key="/laboratory/derection">
              </TabPane>
              <TabPane tab="研究团队" key="/laboratory/team">
              </TabPane>
              <TabPane tab="毕业生" key="/laboratory/graduates">
              </TabPane>
            </Tabs>
        </div>
        <div className='lab-body'>
          <div className='lab-content'>
              <Form className="form-lable-info">
                <Item label='标题'>
                  {
                    getFieldDecorator('title', {
                      initialValue: title,
                      rules: [
                        {required: true, message: '必须指定标题'},
                      ]
                    })(
                      <Input placeholder='请输入标题' />
                    )
                  }
                </Item>
                <Item label='内容'>
                  {/* <RichTextEdit ref={this.editor} context={description} changeRichText = {(description) => this.setState({description})}/> */}
                  {
                    description?<BraftEditor  ref={this.editor} context={description}></BraftEditor>:null
                  }

                  {
                    description === null ?<BraftEditor  ref={this.editor} context={''}></BraftEditor>:null
                  }
                  
                </Item>
                <Row type="flex" justify="end">
                    <Col>
                      <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.saveData}>保存</Button>
                      <Button type='primary' style={{width:180,height:40,marginRight:20,cursor:'pointer'}} onClick={this.publishData}>发布</Button>
                      <Button type='danger' style={{width:180,height:40,cursor:'pointer'}} onClick={this.uploadImg}>取消</Button>
                    </Col>
                  </Row>
              </Form>
            </div>
        </div>
      </div>
      
    )
  }
}

export default Form.create()(LabDescription)