import { withTranslation } from "react-i18next";
import Form from "../common/Form";
import Joi from "joi";
import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import { parseFromTimePickerToOffsetDateTime } from "../../services/DateParser";
import { createActivity } from "../../services/ActivityService";

class ActivityForm extends Form {

  state = {
    data: {
      activityNumber: '',
      title: '',
      startDate: new Date(),
      duration: '',
      trainer: '',
      capacity: ''
    },
    errors: {}
  };

  fieldRestrictions = {
    activityNumber: Joi.string().pattern(/^ACT[0-9]{3}$/).required(),
    title: Joi.string().required(),
    startDate: Joi.any().required(),
    duration: Joi.number().min(30).required(),
    trainer: Joi.string().required(),
    capacity: Joi.number().min(1)
  }

  schema = Joi.object({
    activityNumber: this.fieldRestrictions.activityNumber,
    title: this.fieldRestrictions.title,
    startDate: this.fieldRestrictions.startDate,
    duration: this.fieldRestrictions.duration,
    trainer: this.fieldRestrictions.trainer,
    capacity: this.fieldRestrictions.capacity
  })

  continueSubmitting = async () => {
    const { t } = this.props;
    const {
      activityNumber,
      title,
      startDate,
      duration,
      trainer,
      capacity
    } = this.state.data;

    await createActivity({
      activityNumber: activityNumber,
      title: title,
      startDate: parseFromTimePickerToOffsetDateTime(startDate),
      duration: parseInt(duration),
      trainer: trainer,
      capacity: parseInt(capacity)
    }, t).then(resp => {
      if (resp && resp.status === 201) {
        this.props.history.push('/activities');
      }
    });
  }

  handleDateChange = (date) => {
    let newState = { ...this.state };
    newState.data.startDate = date.nativeEvent.text;
    this.setState({ newState });
  }

  render() {
    const { t } = this.props;
    let currentDate = new Date();
    return (
        <div className="card-header mt-5 w-50 mx-auto">
          <h1 className="text-center">{t('newActivity')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("activityNumber", t("number"))}
            {this.renderInput("title", t("name"))}
            {this.renderInput("duration", t("duration"), 'number')}
            {this.renderInput("trainer", t("trainer"))}
            {this.renderInput("capacity", t("capacity"), 'number')}
            <label>{t('startDate')}</label>
            <DateTimePickerComponent
                onChange={this.handleDateChange}
                strictMode={true}
                value={`${currentDate.getDate()}-${currentDate.getMonth()+1}-${currentDate.getFullYear()} ${currentDate.getHours()}:${currentDate.getMinutes()}`}
                className="mt-2 fs-5"
                placeholder={t("chooseDateTime")}
                format="dd-MM-yyyy HH:mm"/>
            {this.renderButton(t("create"))}
          </form>
        </div>
    );
  }
}

export default withTranslation()(ActivityForm);