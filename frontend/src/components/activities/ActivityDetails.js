import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Activity } from "../../model/Activity";
import UpdateActivityModal from "./UpdateActivityModal";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from "../../config.json";
import { getActivity } from "../../services/ActivityService";

class ActivityDetails extends Details {

  state = {
    data: {
      activity: Activity
    }
  }

  async componentDidMount() {
    await this.updateDetails();

    const myModalEl = document.getElementById('updateActivityModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      this.updateDetails();
    })
  }

  updateDetails = async () => {
    const pathParam = this.props.match.params.number;
    const { t } = this.props;
    let currentState = { ...this.state };
    const fetched = await getActivity(pathParam);
    currentState.data.activity = new Activity(
        fetched.number,
        fetched.title,
        parseFromOffsetDateTimeToLegibleFormat(fetched.startDate),
        fetched.duration,
        fetched.trainer,
        fetched.active ? t('active') : t('inactive'),
        fetched.capacity,
        fetched.modifiedBy,
        fetched.createdBy,
        parseFromOffsetDateTimeToLegibleFormat(fetched.creationDate),
        parseFromOffsetDateTimeToLegibleFormat(fetched.modificationDate)
    );
    this.setState({ currentState });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <UpdateActivityModal activity={this.state.data.activity}/>
          <div className="card-header">
            <h1>{t('activityDetails')}</h1>
            {this.renderRefreshButton(this.updateDetails, t)}
            {getCurrentRole() === config.TRAINER ?
                this.renderUpdateButton('updateActivityModal', t('update'))
                : ''}
          </div>
          {this.renderField('number', t('number'),
              this.state.data.activity)}
          {this.renderField('title', t('name'), this.state.data.activity)}
          {this.renderField('startDate', t('startDate'),
              this.state.data.activity)}
          {this.renderField('duration',
              t('duration'), this.state.data.activity)}
          {this.renderField('trainer', t('trainer'),
              this.state.data.activity, true)}
          {this.renderField('active', t('active'), this.state.data.activity)}
          {this.renderField('capacity', t('capacity'),
              this.state.data.activity)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.activity, true)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.activity, true)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.activity)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.activity)}
        </div>
    );
  }
}

export default withTranslation()(ActivityDetails);