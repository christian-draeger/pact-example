import React, {Component} from 'react';
import {getEndpoint} from "./utils/environment";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {data: {}};
    }

    componentWillMount() {
        fetch(getEndpoint(), {
            method: 'GET',
            mode: "cors"
        })
            .then(response => response.json())
            .then((data) => {
                this.setState({data})
            });
    }

    render() {
        return (
            <div>
                <div>{this.state.data.firstName}</div>
                <div>{this.state.data.lastName}</div>
                <div>{this.state.data.age}</div>
            </div>
        );
    }
}

export default App;
