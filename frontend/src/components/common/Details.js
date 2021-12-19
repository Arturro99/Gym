import { Component } from "react";

export default class Details extends Component {

  renderUpdateButton(modalId, label) {
    console.log(modalId)
    return (
        <button className="btn btn-primary float-end"
                data-bs-toggle='modal' data-bs-target={`#${modalId}`}>
          {label}
        </button>
    )
  }

  renderField(paramName, label, data) {
    return (
        <div className="row row-cols-2 justify-content-center">
          <label
              className="col-md-4 fw-bold text-uppercase my-2 text-start">{label}:</label>
          <label className="col-md-4 my-2 text-start">{data[paramName]}</label>
        </div>
    )
  }
}