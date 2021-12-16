import { Component } from "react";

export default class Details extends Component {

  renderField(paramName, label, data) {
    return (
        <div className="row row-cols-2 justify-content-center">
          <label className="col-md-4 fw-bold text-uppercase my-2 text-start">{label}:</label>
          <label className="col-md-4 my-2 text-start">{data[paramName]}</label>
        </div>
    )
  }
}