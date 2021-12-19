import React, { ReactFragment, useState } from "react";
import MainComponent from "./components/MainComponent";
import LoginForm from "./components/LoginForm";

function App() {
  const [isLoginShowed, setIsLoginShowed] = useState(false);

  const handleLoginClick = () => {
    setIsLoginShowed((isLoginShowed) => !isLoginShowed);
  }

  return (
    <div>
      <LoginForm isVisible={isLoginShowed}/>
      <MainComponent onLoginClick={handleLoginClick}/>
    </div>
  );
}

export default App;
