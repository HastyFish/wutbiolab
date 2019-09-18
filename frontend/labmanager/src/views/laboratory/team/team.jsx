import React, { Component } from 'react';
import {
  Button,
  Collapse,
  Icon,
  Modal,
  Tabs,
  message
} from 'antd';


import MentorClasifi from './teamadd/mentor-classifi';
import AddlabDetails from './teamadd/add-person';
import {reqResearchTeam, reqSortTeam, reqPublishResearchTeam} from '@/api';

import './team.less';

const { Panel } = Collapse;
const { TabPane } = Tabs;

export default class Team extends Component {
  

  constructor(props){
    super(props);
    // 创建用来保存ref标识的标签对象的容器
    this.addlabDetails = React.createRef()
    this.mentorCategoryName = React.createRef()

    this.state = {
      modalVisible:false,  //编辑的模态框
      activeKey:'1',       //Tab活动页，1表示编辑岗位分类， 2表示新增岗位
      teamList:[
      ],     //导师分类及具体人员列表
    }

  }

  //获取所有分类的函数
  getAllTeamData = async () => {
    //获取研究团队分类数据
    const result = await reqResearchTeam();
    if(result.code === 0){
      const teamList = result.result;
      this.setState({
        teamList
      })
    }else{
      message.error('获取研究团队信息失败，请稍后再试!')
    }
  }

  handleCancel = () => {
    //alert('关闭模态框')
    //回到初始状态
    this.setState({ modalVisible: false , activeKey:'1'});

    //初始化编辑导师分类的表单
    this.MentorClassForm.resetFields();
    //清除当前一级分类中recruList中的disabled属性(去除)
    const teamList = this.state.teamList.map(recruit => {
      return {
        mentorCategoryId:recruit.mentorCategoryId,
        mentorCategoryName:recruit.mentorCategoryName,
        labDetails:recruit.labDetails
      }
    });

    this.setState({
      teamList
    })

    //清除新增岗位的表单
    if(this.jobForm){
      this.jobForm.resetFields();
    }
  };

  //Tab内容变化调用的函数
  changeTab = (key) => {
    this.setState({
      activeKey:key
    })
  }

  //Collapse折叠面板打开关闭事件
  changeCollapse = async (key) => {
    //console.log(key);
    //如果key存在说明是打开某个折叠面板，则加载对应的折叠面板数据
    //if(key){
      //打开某个折叠面板则获取对应岗位分类下所有的数据
      // const result = await reqJobsById(key);
      // if(result.code === 0){
      //   const children = result.result;
      //   if(children.length > 0){
      //     const teamList = this.state.teamList.map(recruit => {
      //       if(recruit.id === key * 1){
      //         recruit.children = children
      //       }
      //       return recruit
      //     })
      //     this.setState({
      //       teamList
      //     })
      //   }
      // }
    //}else{
      //关闭折叠面板则不做处理
    //}
  }


  //跳转到更新人员编辑页面
  editlabDetails = async (id) => {
    //获取岗位招聘信息
    // const result = await reqRecruit(id);
    // if(result.code === 0){
    //   //获取岗位信息成功
    //   const recruit = result.result;
    //   this.props.history.push('/team/edit', {recruit})
    // }
    this.props.history.push('/laboratory/team/edit', {id})
  }

  
  //调整人员顺序
  editOrder = async (pindex,index) => {
    //判断当前元素是否为第一个
    if(index === 0){
      message.warn('无法继续向前移动')
    }else {
      //获取对应的二级分类列表
      const {teamList} = this.state;
      const {labDetails} = teamList[pindex];
      //交换顺序
      let temp = labDetails[index];
      labDetails[index] = labDetails[index - 1];
      labDetails[index - 1] = temp;
      //循环修改labDetails的mentorOrder
      labDetails.forEach((item,index) => {
        item.mentorOrder = index + 1;
      })
      const result = await reqSortTeam(labDetails);
      if(result.code === 0){
        this.setState({
          teamList
        })
      }else{
        message.error('排序失败，请稍后再试')
      }
    }
  }


  //发布按钮点击事件
  publish = async () => {
    const result = await reqPublishResearchTeam();
    if(result.code === 0){
      message.success('发布成功')
    }else{
      message.error('发布失败，请稍后再试')
    }
  }



  async componentDidMount(){
    //获取研究团队分类数据
    const result = await reqResearchTeam();
    if(result.code === 0){
      const teamList = result.result;
      this.setState({
        teamList
      })
    }else{
      message.error('获取研究团队信息失败，请稍后再试!')
    }
  }


  render() {
    const {teamList, modalVisible, activeKey} = this.state;
    
    return (
      <div className="team">
        <div className="team-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="机构概况" key="/laboratory">
            </TabPane>
            <TabPane tab="研究方向" key="/laboratory/derection">
            </TabPane>
            <TabPane tab="研究团队" key="/laboratory/team">
            </TabPane>
            <TabPane tab="毕业生" key="/laboratory/graduates">
            </TabPane>
          </Tabs>
        </div>
        <div className="team-body">
          
          <div className='team-content'>
            <Button type="primary" style={{ width: 180, height: 40, margin: '0 0 20px 0' }} onClick={() => this.setState({modalVisible:true})}>编辑</Button>
            <Button type="primary" style={{ width: 180, height: 40, margin: '0 0 20px 20px' }} onClick={this.publish}>发布</Button>
            <Collapse accordion expandIconPosition='right' onChange={this.changeCollapse}>
            {
              teamList.map((mentor,pindex) => (
                  <Panel header={mentor.mentorCategoryName} key={pindex}>
                      {
                        mentor.labDetails ? mentor.labDetails.map((c,index) => (
                            <p className='panelitem' key={c.id}>
                              {c.mentorName}
                              <span style={{marginLeft:20}} className="icospan" onClick={() => this.editOrder(pindex,index)}>
                                <Icon type="up"/>
                                <span className="iconame">排序</span>
                              </span>
                              <span className="icospan" onClick={() => this.editlabDetails(c.id)}>
                                <Icon type="edit"/>
                                <span className="iconame">编辑</span>
                              </span>
                            </p>
                          ) 
                        ): null
                      }
                  </Panel>
                )
              )
            }
          </Collapse>
            <Modal 
              visible={modalVisible} 
              onCancel={this.handleCancel}
              footer = {false}
            >
            <Tabs activeKey={activeKey} onChange={this.changeTab}>
              <TabPane tab="编辑导师分类" key="1">
                <MentorClasifi ref={this.mentorCategoryName} teamList={teamList} setForm={(form) => this.MentorClassForm = form} closeModal={this.handleCancel} getAllTeamData={this.getAllTeamData} />
              </TabPane>
              <TabPane tab="新增人员名单" key="2">
                <AddlabDetails ref={this.addlabDetails} teamList={teamList} setForm={(form) => this.jobForm = form} closeModal={this.handleCancel} getAllTeamData={this.getAllTeamData} />
              </TabPane>
            </Tabs>
            </Modal>
          </div>
        </div>
      </div>
    )
  }
}