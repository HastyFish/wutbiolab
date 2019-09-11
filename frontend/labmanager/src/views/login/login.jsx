import React, { PureComponent } from 'react';
import {
  Form,
  Icon,
  Input,
  Button,
  Modal,
  Row,
  Col
} from 'antd';
import logo from '../../assets/images/logo.png';
import bgCom from './images/bgCom.png'
import './login.less';
import storageUtils from '../../utils/storageUtils.js'
// import Register from '../../components/register/register'
import {reqLogin} from '@api/index'


class Login extends PureComponent {
  state = {
    modalVisible: false,
    msg:""
  };
  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }
  handleSubmit = (event) => {
    event.preventDefault();

    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        //console.log('提交登陆的ajax请求', values)
        // 请求登陆
        const result = await reqLogin(values);
        this.setState({msg:result.msg})
        if(result.code === -1){
          return 
        }
        //console.log('请求成功', result.data);
        if (result.code === 0) {
          const { username, token,authorities } = result.result;
          //提示登录成功
          storageUtils.saveUser({username,authorities}); //保存到localStorage中
          storageUtils.saveToken(token); //保存到localStorage中
        }

      } else {
        console.log('检验失败');
      }
    });

    //const form = this.props.form;
    //const values = form.getFieldsValue();
    //console.log(values);
  }

  //对密码进行验证
  validatePwd = (rule, value, callback) => {
    if (!value) {
      callback('密码必须输入'); //验证失败，并指定提示文本
    } 
    // else if (value.length < 3) {
    //   callback('密码长度不能小于3位'); //验证失败，并指定提示文本
    // } else if (value.length > 12) {
    //   callback('密码长度不能大于12位 '); //验证失败，并指定提示文本
    // } else if (!/^[a-zA-Z0-9_]+$/.test(value)) {
    //   callback('密码必须时英文，数字或下划线组成'); //验证失败，并指定提示文本
    // } 
    else {
      callback()  //验证通过
    }
  }

  componentDidMount(){
    //如果用户已经登录，自动跳转到管理页面
    const user = storageUtils.getUser();
    if (user && user.username) {
      // return <Redirect to="/user" />
      this.props.history.replace('/user');
    }
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <div className="login">
        <header className="login-header">
          <div className="login-header-content">
            <img src={logo} alt="LOGO" />
            <span className="line"></span>
            <h1>后台管理中心</h1>
          </div>
        </header>
        <section className="login-content">
          <div className="login-content-login">
            <img src={bgCom} alt="logo"/>
            <div className="login-content-form">
            <h2>账号登录</h2>
              <Form onSubmit={this.handleSubmit} className="login-form">
                <Form.Item>
                  {
                    getFieldDecorator('username', {
                      //声明式验证：直接使用别人定义好的规则进行验证
                      rules: [
                        { required: true, whitespace: true, message: '用户名必须输入!' },
                        // { min: 3, message: '用户名至少3位' },
                        // { max: 12, message: '用户名最多12位' },
                        { pattern: /^[a-zA-Z0-9_]+$/, message: "用户名必须时英文，数字或下划线组成" }
                      ],
                    })(
                      <Input
                        prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                        placeholder="请输入用户名"
                      />
                    )
                  }

                </Form.Item>
                <Form.Item>
                  {getFieldDecorator('password', {
                    rules: [
                      {
                        //自定义规则验证
                        validator: this.validatePwd
                      }
                    ],
                  })(
                    <Input
                      prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                      type="password"
                      placeholder="请输入密码"
                    />,
                  )}
                </Form.Item>
                <Form.Item>
                  <p  className="login-form-forgot">
                    <span onClick={() => this.setModalVisible(true)}>
                      忘记密码？
                    </span>
                  </p>
                  <Button type="primary" htmlType="submit" className="login-form-button">
                        登录
                    </Button>
                </Form.Item>
              </Form>
              <p style={{padding:"0 43px",color:"red"}}>{this.state.msg}</p>
            </div>
          </div>
        </section>
        <footer className="login-footer">
          <p>©版权所有 古奥基因 GOOALGENE 2019 鄂ICP备16015451号-1</p>
        </footer>
        <Modal
          title="忘记密码？"
          centered
          wrapClassName="forget-pwd"
          maskClosable={false}
          keyboard={false}
          footer={null}
          visible={this.state.modalVisible}
          onOk={() => this.setModalVisible(false)}
          onCancel={() => this.setModalVisible(false)}
        >
          <p className="forget-content">
            请使用如下2种方式来重置密码：
          </p>
            <Row type="flex" justify="center" className="forget-us">
            <Col span={10}>
              <span className="line"></span>
            </Col>
            <Col span={4}><span className="txt">联系方式</span></Col>
            <Col span={10}>
              <span className="line"></span>
            </Col>
          </Row>
          <Row type="flex" justify="center" className="forget-method">
            <Col span={10}>
              <span className="forget-emali">admin@gooalgene.com</span>
            </Col>
            <Col span={4}><span className="or">或</span></Col>
            <Col span={10}>
              <span className="forget-tel">+86 12345678912</span>
            </Col>
          </Row>
        </Modal>
      </div>
    )
  }
}

const WrapLogin = Form.create()(Login);
export default WrapLogin;