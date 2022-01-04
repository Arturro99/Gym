import { withTranslation } from "react-i18next";
import { Component } from "react";
import {
  addAccessLevel,
  getAccessLevelsByAccount,
  removeAccessLevel
} from "../../services/AccessLevelService";

import config from '../../config.json'

class AccessLevelModal extends Component {

  state = {
    data: {
      roles: ''
    },
    modalId: 'accessLevelModal',
    name: 'assignAccessLevels',
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

  handleSubmit = async (e) => {
    e.preventDefault();
    const { t, account } = this.props;
    const { roles } = this.state.data;
    const addedRoles = roles.filter(
        role => !this.props.accessLevels.includes(role));
    const removedRoles = this.props.accessLevels.filter(
        role => !roles.includes(role));

    addedRoles.forEach(role => {
      addAccessLevel(role, account.login, t);
    })
    removedRoles.forEach(role => {
      removeAccessLevel(role, account.login, t);
    })
  }

  componentDidMount() {
    console.log(this.props.accessLevels)
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('show.bs.modal', () => {
      this.resetData();
    })
  }

  async resetData() {
    let currentData = { ...this.state.data };
    const accessLevels = await getAccessLevelsByAccount(this.props.account.login);
    const levels = accessLevels.map(acc => acc.level);
    currentData.roles = levels;
    this.setState({ data: currentData });
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
                         id={config.ADMIN}
                         onChange={this.handleCheck}
                         checked={roles.includes(
                             config.ADMIN)}/>
                  <label className="form-check-label text-light fs-4"
                         htmlFor="admin">
                    {t('admin')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input fs-4" type="checkbox"
                         value=""
                         id={config.TRAINER}
                         onChange={this.handleCheck}
                         checked={roles.includes(
                             config.TRAINER)}/>
                  <label className="form-check-label text-light fs-4"
                         htmlFor="trainer">
                    {t('trainer')}
                  </label>
                </div>
                <div className="form-check">
                  <input className="form-check-input fs-4" type="checkbox"
                         value=""
                         id={config.CLIENT}
                         onChange={this.handleCheck}
                         checked={roles.includes(
                             config.CLIENT)}/>
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
                        data-bs-target='#accessLevelModal'>
                  {t('cancel')}</button>
              </div>
            </div>
          </div>
        </div>
    );
  }

}

export default withTranslation()(AccessLevelModal);