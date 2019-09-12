
/** 
 * 新增岗位组件
 * 
*/
import React,{Component} from 'react';
import {
  Form,
  Select,
  Input,
  Button,
} from 'antd';
import propTypes from 'prop-types'
//import {reqAddJob} from '../../api';


const {Item} = Form;
const {Option} = Select;

class AddPerson extends Component{
  static propTypes = {
    teamList: propTypes.array.isRequired,
  }

  constructor(props){
    super(props)

    const {teamList} = this.props;
    this.state = {
      teamList,  //岗位分类及具体岗位列表
    }
  }

  savePerson = () => {
    this.props.form.validateFields(async (err, values) => {
      if(!err){
        //发送新增人员请求
        //const {mentorId,name} = values;
        // const result = await reqAddJob({mentorId,name});
        // if(result.code === 0){
        //   message.success('新增岗位成功')
        // }
        //关闭模态框
        this.props.closeModal();

        //清除输入数据
        this.props.form.resetFields();
      }
    })
  }

  // componentWillReceiveProps(nextProps){
  //   const {teamList} = nextProps;
  //   this.setState({
  //     teamList
  //   })
  // }

  componentDidMount(){
    this.props.setForm(this.props.form);
  }

  render(){
    const {teamList} = this.state;
    //console.log(teamList);
    const {getFieldDecorator} = this.props.form;

    return (
      <Form>
        <Item>
          {
            getFieldDecorator('mentorId', {
              initialValue: teamList[0] ? teamList[0].id : '',
              rules: [
                {required: true, message: '必须选择导师分类'},
              ]
            })(
              <Select>
                {/* <Option value='0'>全部</Option> */}
                {
                  teamList.map((item,index) => (
                  <Option key={index} value={item.id}>{item.mentorClassifi}</Option>
                  ))
                }
              </Select>
            )
          }
        </Item>
        <Item>
          {
            getFieldDecorator('name', {
              initialValue: '',
              rules: [
                {required: true, message: '必须指定人员名称'},
              ]
            })(
              <Input placeholder="请输入人员名称" />
            )
          }
        </Item>
        <Item>
          <Button type="primary" onClick={this.savePerson}>
            保存
          </Button>
        </Item>
      </Form>
    )
  }
}

export default Form.create()(AddPerson)