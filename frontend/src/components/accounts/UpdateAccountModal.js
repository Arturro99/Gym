import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import Joi from "joi";

class UpdateAccountModal extends Form {

  state = {
    data: {
      firstName: this.props.account.firstName,
      lastName: this.props.account.lastName,
      phoneNumber: this.props.account.phoneNumber,
      gymMember: this.props.account.gymMember
    },
    modalId: 'updateAccountModal',
    name: 'updateAccount',
    errors: []
  }

  fieldRestrictions = {
    firstName: Joi.string().required(),
    lastName: Joi.string().required(),
    phoneNumber: Joi.string().min(9).max(11).optional().allow(""),
    gymMember: Joi.any()
  }

  schema = Joi.object({
    firstName: this.fieldRestrictions.firstName,
    lastName: this.fieldRestrictions.lastName,
    phoneNumber: this.fieldRestrictions.phoneNumber,
    gymMember: this.fieldRestrictions.gymMember
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData.firstName = this.props.account.firstName;
      currentData.lastName = this.props.account.lastName;
      currentData.phoneNumber = this.props.account.phoneNumber;
      currentData.gymMember = this.props.account.gymMember;
      this.setState({ data: currentData });
    })
  }

  continueSubmitting = (e) => {
    const { firstName, lastName, phoneNumber, gymMember } = this.props.account;
    let updatedData = [];
    if (firstName !== this.state.data.firstName) {
      updatedData.push(this.state.data.firstName)
    }
    if (lastName !== this.state.data.lastName) {
      updatedData.push(this.state.data.lastName)
    }
    if (phoneNumber !== this.state.data.phoneNumber) {
      updatedData.push(this.state.data.phoneNumber)
    }
    if (gymMember !== this.state.data.gymMember) {
      updatedData.push(this.state.data.gymMember)
    }
    console.log('Changed fields: ' + updatedData)
  }

  handleCheck = (event) => {
    let currentData = { ...this.state.data };
    const { t } = this.props;
    if (event.target.checked) {
      currentData.gymMember = t('yes');
    } else {
      currentData.gymMember = t('no');
    }
    this.setState({ data: currentData });
  }

  render() {
    const { modalId, name, data } = this.state;
    const { t } = this.props;
    return (
        <div className="modal fade" id={this.state.modalId} tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t(name)}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('firstName', t('firstName'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.firstName)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('lastName', t('lastName'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.lastName)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('phoneNumber', t('phoneNumber'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.phoneNumber)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('gymMember', t('gymMember'), 'checkbox',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.gymMember, this.state.data.gymMember === t('yes'),
                    this.handleCheck)}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}
                        onClick={this.handleSubmit}
                        disabled={this.validate()}>
                  {t('save')}</button>
                <button type="button" className="btn btn-secondary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}>
                  {t('cancel')}</button>
              </div>
            </div>
          </div>
        </div>
    )
  }
}

export default withTranslation()(UpdateAccountModal);