import React from "react";

const Input = ({ name, label, error, ...rest }) => {
  return (
      <form>
        <label htmlFor={name} className="form-label">{label}</label>
        <input autoFocus
               name={name}
               {...rest}
               id={name}
               className="form-control"/>
        {error && <div className="alert alert-danger">{error}</div>}
      </form>
  )
}

export default Input;