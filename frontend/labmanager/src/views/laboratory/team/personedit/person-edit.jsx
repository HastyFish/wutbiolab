/** 
 * 招聘要求编辑页面
*/

import React, { Component } from 'react';
import {
  Form,
  Button,
  Input,
  message
} from 'antd';


import BraftEditor from '@/components/rich-text-editor/rich-text-editor';
import {reqGetPerson, reqSavePerson, reqPublishPerson} from '@/api';
import './person-edit.less';
const { Item } = Form;

class PersonEdit extends Component {

  constructor(props) {
    super(props);
    // 创建用来保存ref标识的标签对象的容器
    this.editor = React.createRef()

    const { id } = this.props.location.state;
    this.state = {
      id,
      person:{},
    }
  }


  //保存招聘信息
  savePerson = () => {
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        //更新人员信息
        const {mentorName} = values;
        const context = this.editor.current.getContext();
        const {person} = this.state;
        person.context = context;
        person.mentorName = mentorName;
        person.labCategoryId = 3;
        const result = await reqSavePerson(person);
        if (result.code === 0) {
          //说明更新成功
          this.props.history.replace('/laboratory/team');
        } else {
          message.error('保存失败，请稍后再试');
        }
      }
    })
  }


  //发布招聘信息
  publishPerson = () => {
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        //更新人员信息
        const {mentorName} = values;
        const context = this.editor.current.getContext();
        const {person} = this.state;
        person.context = context;
        person.mentorName = mentorName;
        person.labCategoryId = 3;
        const result = await reqPublishPerson(person);
        if (result.code === 0) {
          //说明更新成功
          this.props.history.replace('/laboratory/team');
        } else {
          message.error('发布失败，请稍后再试');
        }
      }
    })
  }

  async componentDidMount(){
    //获取当前人员的信息
    const {id} = this.state;
    const result = await reqGetPerson(id);
    if(result.code === 0){
      const person = result.result;
      this.setState({person});
    }else{
      message.error('获取个人信息失败, 请稍后再试')
    }
  }

  render() {
    // // 指定Item布局的配置对象
    const formItemLayout = {
      labelCol: { span: 1 },  // 左侧label的宽度
      labelAlign: 'right',
      wrapperCol: { span: 8 }, // 右侧包裹的宽度
    }

    const { getFieldDecorator } = this.props.form

    const { person } = this.state;
    const { mentorName, context } = person;

    return (
      <div className='personEdit'>
        <div className='personEdit-title'>
          个人信息编辑
        </div>
        <div className='personEdit-body'>
          {/* <div className='title'>{mentorName}</div> */}
          <div className="content">
            <Form className="personEdit-form">
              <Item label='姓名' {...formItemLayout}>
              {
                getFieldDecorator('mentorName', {
                  initialValue: mentorName || '',
                  rules: [
                    {required: true, message: '必须指定人员名称'},
                  ]
                })(
                  <Input placeholder="请输入人员名称" />
                )
              }
              </Item>
              <Item label='描述' className="personEdit-editor" {...formItemLayout}>
                {/* <RichTextEdit ref={this.editor1} context = {description} /> */}
                {context ? <BraftEditor ref={this.editor} context={context}></BraftEditor>:null}
                {!context ? <BraftEditor ref={this.editor} context={''}></BraftEditor>:null}
              </Item>
            </Form>
            <div className='footer' style={{ width: '100%' }}>
              <Button type="primary" style={{ marginLeft: '8%', width: 150, height: 40 }} onClick={this.savePerson}>保存</Button>
              <Button type="primary" style={{ margin: '0 25px', width: 150, height: 40 }} onClick={this.publishPerson}>发布</Button>
              <Button type="danger"  style={{width: 150, height: 40 }} onClick={() => this.props.history.replace('/laboratory/team')}>取消</Button>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Form.create()(PersonEdit)