/** 
 * 编辑岗位分类组件
*/
import React,{Component} from 'react';
import {
  Form,
  Icon,
  Input,
  Modal,
  Button,
  message
} from 'antd';
import propTypes from 'prop-types'
import AddMentorClassifi from './add-mentorclassifi';
import {reqAddTeamClassifi, reqDeleteTeamClassifi, reqResearchTeam, reqUpdateTeamClassifi} from '@/api';
const {Item} = Form;

class MentorClasifi extends Component{

  static propTypes = {
    teamList: propTypes.array.isRequired,
    setForm: propTypes.func.isRequired,
    getAllTeamData: propTypes.func.isRequired,
  }

  constructor(props){
    super(props)

    const {teamList} = this.props;
    this.state = {
      teamList,  //岗位分类及具体岗位列表,
      AddModalVisible:false, //点击添加岗位分类显示的模态框
    }
  }

  //新增岗位分类
  addMentorClass = () => {
    //发送新增岗位分类
    this.MentorClassForm.validateFields(async (err, values) => {
      if (!err) {
        // 隐藏确认框
        this.setState({
          AddModalVisible: false
        })
        // 收集数据, 并提交添加分类的请求
        const {mentorClassifi} = values
        
        //发送新增岗位分类请求
        const result = await reqAddTeamClassifi([{
          category:mentorClassifi
        }]);
        if(result.code === 0){
          message.success('添加岗位分类成功');
          //更新当前的一级分类
          const result = await reqResearchTeam();
          if(result.code === 0){
            //更新当前组件
            const teamList = result.result;
            this.setState({teamList})
            //调用更新父组件中所有岗位分类的方法
            this.props.getAllTeamData();
          }
        }

        // 清除输入数据
        this.MentorClassForm.resetFields();

      }
    })
  }

  //编辑岗位分类
  editMentorClass = (mentor) => {
    //获取当前的mentorCategoryId
    const {teamList} = this.state;
    teamList.forEach((item) => {
      if(item.mentorCategoryId === mentor.mentorCategoryId){
        item.disabled = true;
      }
    })
    this.setState(state => {
      return {teamList}
    })
  }

  //删除研究团队一级分类
  deleteMentorClass = async (mentor) => {
    //获取当前的id
    const {mentorCategoryId} = mentor;
    const result = await reqDeleteTeamClassifi(mentorCategoryId);
    if(result.code === 0){
      //删除成功，删除当前一级分类列表中的对应数据
      const teamList = this.state.teamList.filter(recruit => recruit.mentorCategoryId !== mentorCategoryId);
      this.setState({
        teamList
      })
      //更新当前的一级分类
      const result = await reqResearchTeam();
      if(result.code === 0){
        //更新当前组件
        // const teamList = result.result;
        // this.setState({teamList})
        //调用更新父组件中所有岗位分类的方法
        this.props.getAllTeamData();
      }

    }else{
      //删除失败
      message.error('删除岗位分类失败, 请稍后再试');
    }
  }

  //保存当前编辑的岗位
  saveMentorClassifi = () => {
    this.props.form.validateFields(async (err,values) => {
      if(!err){
        //发送编辑请求
        //alert('模拟发送编辑岗位分类请求')

        let params = [];
        Object.keys(values).forEach(c => {
          params.push({
            id:c,
            category:values[c]
          })
        })
        //发送更新的请求
        const result = await reqUpdateTeamClassifi(params);
        if(result.code === 0){
          //更新当前一级分类
          const result = await reqResearchTeam();
          if(result.code === 0){
            //更新当前组件
            const teamList = result.result;
            this.setState({teamList})
            //调用更新父组件中所有岗位分类的方法
            this.props.getAllTeamData();
          }
          
        }

        //关闭模态框
        this.props.closeModal();
      }
    })
  }

  
  handleMentorClass = () => {
    //回到初始状态
    this.setState({AddModalVisible:false});

    // 清除输入数据
    this.MentorClassForm.resetFields();
  };


  showAddItem = () => {
    //显示模态框
    this.setState({AddModalVisible:true});
  }


  componentWillReceiveProps(nextProps){
    //一旦接收到新数据则重置其中的disabled属性
    const {teamList} = nextProps;
    this.setState({
      teamList
    })
  }

  componentDidMount(){
    this.props.setForm(this.props.form);
  }


  render(){
    const {teamList, AddModalVisible} = this.state;
    const {getFieldDecorator} = this.props.form;

    return (
      <Form>
        {
          teamList.map((mentor,index) => {
            const name = mentor.mentorCategoryId
            return (
              <Item key={index}>
                {
                  getFieldDecorator(`${name}`, {
                    initialValue: mentor.mentorCategoryName,
                    rules: [
                      {required: true, message: '必须指定分类名称'},
                    ]
                  })(
                    <Input disabled={mentor.disabled ? false:true} style={{display:'inline-block',width:200}} placeholder="请输入分类名称"/>
                  )
                }
                <span style={{float:'right'}}>
                  <Icon type="edit" style={{marginRight:20, cursor:'pointer'}} onClick={() => this.editMentorClass(mentor)}/>
                  <Icon type="delete" style={{cursor:'pointer'}} onClick={() => this.deleteMentorClass(mentor)}/>
                </span>
              </Item>
            )
        })
        }
        <Item>
          <Icon type="plus" style={{cursor:'pointer',fontSize:30}} onClick={this.showAddItem}/>
        </Item>
        <Item>
          <Modal title='请输入分类名' 
            visible={AddModalVisible} 
            onCancel={this.handleMentorClass}
            onOk={this.addMentorClass}
            okText="保存"
            cancelText="取消"
          >
            <AddMentorClassifi setForm={(form) => {this.MentorClassForm = form}}/>
          </Modal>
          <Button type="primary" onClick={this.saveMentorClassifi}>
            保存
          </Button>
        </Item>
      </Form>
    )
  }
}

export default Form.create()(MentorClasifi)