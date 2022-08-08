import React, {KeyboardEventHandler, KeyboardEvent} from 'react';
import logo from './logo.svg';
import './App.css';
import Button from '@material-ui/core/Button';
import TextField from "@material-ui/core/TextField";

interface IProps {
}

interface IState {
    loginValue?: string;
    passValue?: string;
}

export default class LoginPage extends React.Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);
        this.state = {
        };


        this.handleClick = this.handleClick.bind(this)
        this.onLoginChange = this.onLoginChange.bind(this)
        this.onPasswordChange = this.onPasswordChange.bind(this)
        // this.handleKeyDown = this.handleKeyDown.bind(this)
    }

    handleKeyDown(e: KeyboardEvent<HTMLInputElement>) {
        console.log(e.key);
        if (e.key == 'Enter') {
            this.handleClick();
        }
    }

    handleClick() {
        window.alert(this.state.loginValue + ":" + this.state.passValue)
    }

    onLoginChange(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({loginValue: e.target.value})
    }

    onPasswordChange(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({passValue: e.target.value})
    }


    render() {
        return (
            <div
                onKeyDown={this.handleKeyDown.bind(this)}>
                {/*<header className="App-header">*/}
                {/*    <img src={logo} className="App-logo" alt="logo"/>*/}
                {/*</header>*/}
                <div className="loginContainer">
                    <TextField
                        value={this.state.loginValue}
                        onChange={this.onLoginChange}
                        id="login">
                    </TextField>
                    <TextField
                        value={this.state.passValue}
                        onChange={this.onPasswordChange}
                        id="password">
                    </TextField>
                    <Button
                        color="secondary"
                        variant="contained"
                        type="submit"
                        onClick={this.handleClick}>
                        Login
                    </Button>
                </div>
            </div>
        );
    }
}
