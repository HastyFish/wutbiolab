/** 
 * 新增岗位分类的Form组件
*/

import React,{Component} from 'react';
import {
  Form,
  Input
} from 'antd';


const {Item} = Form;

class AddMentorClassifi extends Component{

  state = {

  }

  componentWillMount () {
    this.props.setForm(this.props.form)
  }

  render(){
    const {getFieldDecorator} = this.props.form;
    return (
      <Form>
        <Item>
          {
            getFieldDecorator('mentorClassifi', {
              initialValue: '',
              rules: [
                {required: true, message: '必须指定分类名称'},
              ]
            })(
              <Input placeholder="请输入分类名称"/>
            )
          }
        </Item>
      </Form>
    )
  }
}

export default Form.create()(AddMentorClassifi)