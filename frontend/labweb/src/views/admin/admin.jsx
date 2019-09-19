import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import { Layout } from 'antd';
import Footer from '@components/footer'
import Header from '@components/header'
import Home from '@views/home/home'
import Introduction from '@views/introduction/introduction'
import News from '@views/news/news'
import Notices from '@views/notices/notices'
import Scientific from '@views/scientific/scientific'
import Resources from '@views/resources/resources'
import './admin.less'


const { Content } = Layout;

class Admin extends Component {
  constructor(props) {
    super(props);
    this.state = {  }
  }
  render() { 
    return ( 
        <Layout className="layout">
          <Header />
          <Content className="layout-content">
            <Switch>
                <Route path='/introduction' component={Introduction} />
                <Route path='/news' component={News} />
                <Route path='/notices' component={Notices} />
                <Route path='/scientific' component={Scientific} />
                <Route path='/resources' component={Resources} />
                <Route path='/' component={Home} />
            </Switch>
          </Content>
          <Footer />
        </Layout>
     );
  }
}
 
export default Admin;