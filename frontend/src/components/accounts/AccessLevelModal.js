import { withTranslation } from "react-i18next";
import { Component } from "react";

class AccessLevelModal extends Component {

  state = {
    data: {
      roles: this.props.accessLevels
    }
  }

  handleCheck = (event) => {
    let currentData = { ...this.state.data };
    const level = event.target.id
    if (event.target.checked) {
      if (!currentData.roles.includes(level)) {
        currentData.roles.push(level);
      }
    } else {
      if (currentData.roles.includes(level)) {
        currentData.roles = currentData.roles.filter(role => role !== level);
      }
    }
    this.setState({ data: currentData });
  }

  handleCancelClick = () => {
    let currentData = { ...this.state.data };
    currentData.roles = this.props.accessLevels;
    this.setState({ data: currentData });
    console.log(this.props.accessLevels)
  }

  handleSubmit = (e) => {
    e.preventDefault();
    //TODO refresh token
  }

  componentDidMount() {
    const myModalEl = document.getElementById('accessLevelModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData.roles = this.props.accessLevels;
      this.setState({ data: currentData });
    })
  }

  render() {
    const { t } = this.props;
    const { roles } = this.state.data;
    return (
        <div className="modal fade" id="accessLevelModal" tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t('assignAccessLevels')}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <div className="modal-body ms-4">
                <div className="form-check">
                  <input className="form-check-input fs-4" type="checkbox"
                         value=""
                         id="admin"
                         onChange={this.handleCheck}
                         defaultChecked={roles.includes(
                             'admin')}/>
                  <label className="form-check-label text-light fs-4"
                         htmlFor="admin">
                    {t('admin')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input fs-4" type="checkbox"
                         value=""
                         id="trainer"
                         onChange={this.handleCheck}
                         defaultChecked={roles.includes(
                             'trainer')}/>
                  <label className="form-check-label text-light fs-4"
                         htmlFor="trainer">
                    {t('trainer')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input fs-4" type="checkbox"
                         value=""
                         id="client"
                         onChange={this.handleCheck}
                         defaultChecked={roles.includes(
                             'client')}/>
                  <label className="form-check-label text-light fs-4"
                         htmlFor="client">
                    {t('client')}
                  </label>
                </div>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        data-bs-toggle='modal'
                        data-bs-target='#accessLevelModal'
                        onClick={this.handleSubmit}>
                  {t('save')}</button>
                <button type="button" className="btn btn-secondary"
                        data-bs-toggle='modal'
                        data-bs-target='#accessLevelModal'
                        onClick={this.handleCancelClick}>
                  {t('cancel')}</button>
              </div>
            </div>
          </div>
        </div>
    );
  }

}

export default withTranslation()(AccessLevelModal);