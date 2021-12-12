import { withTranslation } from "react-i18next";
import Form from "../common/Form";
import Joi from "joi";
import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'

class ActivityForm extends Form {

  state = {
    data: {
      activityNumber: '',
      name: '',
      startDate: '',
      duration: '',
      trainer: '',
      active: '',
      capacity: ''
    },
    errors: {}
  };

  fieldRestrictions = {
    activityNumber: Joi.string().pattern(/^ACT[0-9]{3}$/).required(),
    name: Joi.string().required(),
    startDate: Joi.any().required(),
    duration: Joi.number().min(30).required(),
    trainer: Joi.string().required(),
    capacity: Joi.number().min(1),
    active: Joi.any()
  }

  schema = Joi.object({
    activityNumber: this.fieldRestrictions.activityNumber,
    name: this.fieldRestrictions.name,
    startDate: this.fieldRestrictions.startDate,
    duration: this.fieldRestrictions.duration,
    trainer: this.fieldRestrictions.trainer,
    capacity: this.fieldRestrictions.capacity,
    active: this.fieldRestrictions.active,
  })

  continueSubmitting = () => {
    console.log(`Activity: ${this.state.data.activityNumber} created`)
  }

  handleDateChange = (date) => {
    let newState = { ...this.state };
    newState.data.startDate = date.nativeEvent.text;
    this.setState({ newState });
  }

  render() {
    const { t } = this.props;
    return (
        <div>
          <h1 className="modal-header">{t('newActivity')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("activityNumber", t("number"))}
            {this.renderInput("name", t("name"))}
            {this.renderInput("duration", t("duration"), 'number')}
            {this.renderInput("trainer", t("trainer"))}
            {this.renderInput("capacity", t("capacity"), 'number')}
            <label>{t('startDate')}</label>
            <DateTimePickerComponent
                onChange={this.handleDateChange}
                strictMode={true}
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