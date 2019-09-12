/** 
 * 招聘要求编辑页面
*/

import React, { Component } from 'react';
import {
  Form,
  Button,
} from 'antd';
// import {
//   Form,
//   Input,
//   Button,
//   message,
//   DatePicker
// } from 'antd';


import BraftEditor from '@/components/rich-text-editor/rich-text-editor';
//import { reqAddJob, reqDeleteperson } from '@/api';
import './person-edit.less';
const { Item } = Form;


class PersonEdit extends Component {

  constructor(props) {
    super(props);
    // 创建用来保存ref标识的标签对象的容器
    this.editor1 = React.createRef();   //保存职位描述

    const { person } = this.props.location.state;
    this.state = {
      person
    }
  }

  /*
 验证招聘人数的自定义验证函数
  */
  validateNumber = (rule, value, callback) => {
    if (value * 1 > 0 && /^[1-9]\d*$/.test(value)) {
      callback() // 验证通过
    } else {
      callback('必须为正整数') // 验证没通过
    }
  }

  //保存招聘信息
  saveperson = () => {
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        //更新岗位信息
        // const { id, categoryId, positionName } = this.state.person
        // const { experience, education, workplace, num, releaseDate } = values;
        // const description = this.editor1.current.getContext();

        // const params = { id, description }
        // const result = await reqAddJob(params);
        // if (result.code === 0) {
        //   //说明更新成功
        //   this.props.history.replace('/person');
        // } else {
        //   message.error('保存失败，请稍后再试');
        // }

        this.props.history.replace('/laboratory/team');
      }
    })
  }

  //删除招聘信息
  deleteperson = async () => {
    // const result = await reqDeleteperson(this.state.person.id);
    // if (result.code === 0) {
    //   //说明更新成功
    //   this.props.history.replace('/person');
    // } else {
    //   message.error('删除失败，请稍后再试');
    // }
    this.props.history.replace('/laboratory/team');
  }


  render() {
    // // 指定Item布局的配置对象
    // const formItemLayout = {
    //   labelCol: { span: 1 },  // 左侧label的宽度
    //   labelAlign: 'right',
    //   wrapperCol: { span: 8 }, // 右侧包裹的宽度
    // }

    // const { getFieldDecorator } = this.props.form

    const { person } = this.state;
    const { name, description } = person;

    return (
      <div className='personEdit'>
        <div className='personEdit-title'>
          个人信息编辑
        </div>
        <div className='personEdit-body'>
          <div className='title'>{name}</div>
          <div className="content">
            <Form className="personEdit-form">
              <Item label='描述' className="personEdit-editor">
                {/* <RichTextEdit ref={this.editor1} context = {description} /> */}
                <BraftEditor ref={this.editor1} context={description}></BraftEditor>
              </Item>
            </Form>
            <div className='footer' style={{ width: '100%' }}>
              <Button type="primary" style={{ marginLeft: '4%', width: 150, height: 40 }} onClick={this.saveperson}>保存</Button>
              <Button type="danger" style={{ margin: '0 25px', width: 150, height: 40 }} onClick={this.deleteperson}>删除</Button>
              <Button style={{ width: 150, height: 40 }} onClick={() => this.props.history.replace('/laboratory/team')}>取消</Button>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Form.create()(PersonEdit)