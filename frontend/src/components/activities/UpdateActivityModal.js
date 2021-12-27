import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import { DateTimePickerComponent } from "@syncfusion/ej2-react-calendars";
import Joi from "joi";
import {
  parseFromOffsetDateTimeToLegibleFormat,
  parseFromTimePickerToOffsetDateTime
} from "../../services/DateParser";
import { getActivity, updateActivity } from "../../services/ActivityService";
import { getDiet } from "../../services/DietService";

class UpdateActivityModal extends Form {

  state = {
    data: {
      title: '',
      startDate: '',
      duration: '',
      capacity: ''
    },
    modalId: 'updateActivityModal',
    name: 'updateActivity',
    errors: []
  }

  fieldRestrictions = {
    title: Joi.string().required(),
    startDate: Joi.any().required(),
    duration: Joi.number().min(30).required(),
    capacity: Joi.number().min(1).required()
  }

  schema = Joi.object({
    title: this.fieldRestrictions.title,
    startDate: this.fieldRestrictions.startDate,
    duration: this.fieldRestrictions.duration,
    capacity: this.fieldRestrictions.capacity,
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('show.bs.modal', () => {
      this.resetData();
    })
  }

  continueSubmitting = async () => {
    const {
      title,
      startDate,
      duration,
      capacity,
      number
    } = this.props.activity;
    const { t } = this.props;
    let newTitle, newStartDate, newDuration, newCapacity = null;
    if (title !== this.state.data.title) {
      newTitle = this.state.data.title;
    }
    if (startDate !== parseFromOffsetDateTimeToLegibleFormat(
        this.state.data.startDate)) {
      newStartDate = this.state.data.startDate;
    }
    if (duration !== this.state.data.duration) {
      newDuration = this.state.data.duration;
    }
    if (capacity !== this.state.data.capacity) {
      newCapacity = this.state.data.capacity;
    }
    await updateActivity({
      number: number,
      title: newTitle,
      startDate: newStartDate,
      duration: newDuration,
      capacity: newCapacity
    }, t)
  }

  async resetData() {
    let currentData = { ...this.state.data };
    const activity = await getActivity(this.props.activity.number);
    currentData.title = activity.title;
    currentData.startDate = activity.startDate;
    currentData.duration = activity.duration;
    currentData.capacity = activity.capacity;
    this.setState({ data: currentData });
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
                {this.renderInput('title', t('name'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.title)}
              </div>
              <div className="modal-body ms-4">
                <label>{t('startDate')}</label>
                <DateTimePickerComponent
                    onChange={this.handleDateChange}
                    strictMode={true}
                    className="mt-2 fs-5"
                    placeholder={parseFromOffsetDateTimeToLegibleFormat(data.startDate)}
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