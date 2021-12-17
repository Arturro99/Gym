import React from "react";

const Input = ({
  name,
  label,
  error,
  labelClassName,
  inputClassName,
  ...rest
}) => {
  return (
      <form id={name}>
        <label htmlFor={name}
               className={labelClassName ? labelClassName
                   : "form-label"}>{label}</label>
        <input autoFocus
               name={name}
               {...rest}
               id={name}
               className={inputClassName ? inputClassName : 'form-control'}/>
        {error && <div className="alert alert-danger">{error}</div>}
      </form>
  )
}

export default Input;