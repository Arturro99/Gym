import React from "react";

const Dropdown = (props) => {
  const {
    onChangeBtn,
    buttonLabel,
    itemName,
    items,
    propertyName,
    labelClassName
  } = props;
  return (
      <div className="form-group">
        <label className={labelClassName}>{itemName}</label>
        <div className="dropdown">
          <button className="btn btn-primary mr-1 dropdown-toggle"
                  type="button" id="dropdownMenuButton"
                  data-bs-toggle="dropdown" aria-haspopup="true"
                  aria-expanded="false">
            {buttonLabel}
          </button>
          <div className="dropdown-menu text-center"
               aria-labelledby="dropdownMenuButton">
            {items.map(
                item => <button className="dropdown-item"
                                type="button"
                                onClick={() => onChangeBtn(
                                    item[propertyName])}>{item[propertyName]}</button>)}
          </div>
        </div>
      </div>
  )
}

export default Dropdown;