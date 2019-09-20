
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
  message
} from 'antd';
import propTypes from 'prop-types'
import {reqAddPerson} from '@/api';


const {Item} = Form;
const {Option} = Select;

class AddPerson extends Component{
  static propTypes = {
    teamList: propTypes.array.isRequired,
    setForm: propTypes.func.isRequired,
    getAllTeamData: propTypes.func.isRequired,
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
        const {mentorCategoryId,mentorName} = values;
        //遍历teamList找到当前的元素
        const {teamList} = this.state
        var item = teamList.find((element) => (element.mentorCategoryId === mentorCategoryId));
        
        const mentorOrder = item.labDetails.length + 1;
        //发送新增人员请求
        const params = {
          mentorCategoryId,     //一级ID
          mentorName,  //二级
          mentorOrder,         //二级排序
          categoryId:3
        }
        const result = await reqAddPerson(params);
        if(result.code === 0){
          message.success('新增人员成功');
          //调用更新父组件中所有岗位分类的方法
          this.props.getAllTeamData();
        }
        //关闭模态框
        this.props.closeModal();

        //清除输入数据
        this.props.form.resetFields();
      }
    })
  }

  componentWillReceiveProps(nextProps){
    const {teamList} = nextProps;
    this.setState({
      teamList
    })
  }

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
            getFieldDecorator('mentorCategoryId', {
              initialValue: teamList[0] ? teamList[0].mentorCategoryId : '',
              rules: [
                {required: true, message: '必须选择导师分类'},
              ]
            })(
              <Select>
                {/* <Option value='0'>全部</Option> */}
                {
                  teamList.map((item,index) => (
                  <Option key={index} value={item.mentorCategoryId}>{item.mentorCategoryName}</Option>
                  ))
                }
              </Select>
            )
          }
        </Item>
        <Item>
          {
            getFieldDecorator('mentorName', {
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