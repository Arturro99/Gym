import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import { DateTimePickerComponent } from "@syncfusion/ej2-react-calendars";
import Joi from "joi";
import {
  parseFromOffsetDateTimeToLegibleFormat,
  parseFromTimePickerToOffsetDateTime
} from "../../services/DateParser";

class UpdateActivityModal extends Form {

  state = {
    data: {
      _name: this.props.activity._name,
      startDate: this.props.activity.startDate,
      duration: this.props.activity.duration,
      capacity: this.props.activity.capacity
    },
    modalId: 'updateActivityModal',
    name: 'updateActivity',
    errors: []
  }

  fieldRestrictions = {
    _name: Joi.string().required(),
    startDate: Joi.any().required(),
    duration: Joi.number().min(30).required(),
    capacity: Joi.number().min(1).required()
  }

  schema = Joi.object({
    _name: this.fieldRestrictions._name,
    startDate: this.fieldRestrictions.startDate,
    duration: this.fieldRestrictions.duration,
    capacity: this.fieldRestrictions.capacity,
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData._name = this.props.activity._name;
      currentData.startDate = this.props.activity.startDate;
      currentData.duration = this.props.activity.duration;
      currentData.capacity = this.props.activity.capacity;
      this.setState({ data: currentData });
    })
  }

  continueSubmitting = (e) => {
    const { _name, startDate, duration, capacity } = this.props.activity;
    let updatedData = [];
    if (_name !== this.state.data._name) {
      updatedData.push(this.state.data._name)
    }
    if (startDate !== parseFromOffsetDateTimeToLegibleFormat(
        this.state.data.startDate)) {
      updatedData.push(this.state.data.startDate)
    }
    if (duration !== this.state.data.duration) {
      updatedData.push(this.state.data.duration)
    }
    if (capacity !== this.state.data.capacity) {
      updatedData.push(this.state.data.capacity)
    }
    console.log('Changed fields: ' + updatedData)
  }

  handleDateChange = (date) => {
    let newData = { ...this.state.data };
    newData.startDate = parseFromTimePickerToOffsetDateTime(
        date.nativeEvent.text);
    this.setState({ data: newData });
  }

  render() {
    const { modalId, name, data } = this.state;
    const { t } = this.props;
    console.log(this.validate())
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
                {this.renderInput('_name', t('name'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data._name)}
              </div>
              <div className="modal-body ms-4">
                <label>{t('startDate')}</label>
                <DateTimePickerComponent
                    onChange={this.handleDateChange}
                    strictMode={true}
                    className="mt-2 fs-5"
                    placeholder={data.startDate}
                    format="dd-MM-yyyy HH:mm"/>
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('duration', t('duration'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.duration)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('capacity', t('capacity'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.capacity)}
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

export default withTranslation()(UpdateActivityModal);