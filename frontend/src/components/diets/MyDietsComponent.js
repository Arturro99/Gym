import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Component } from "react";
import DietsTable from "./DietsTable";
import { getOwnDiets, removeDiet } from "../../services/AccountService";
import { getCurrentUser } from "../../services/AuthenticationService";
import keys from "../../errorKeys.json";

class MyDietsComponent extends Component {

  state = {
    diets: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedDiets = {};

  async componentDidMount() {
    const login = getCurrentUser();
    const diets = await getOwnDiets(login);
    this.setState({
      diets: diets
    })
  }

  handleDelete = async diet => {
    const { t } = this.props;
    const login = getCurrentUser();
    await removeDiet(diet.number, login, t).then(resp => {
      if (resp && resp.status === 200) {
        const originalDiets = this.state.diets;
        const currentDiets = originalDiets.filter(
            die => die.number !== diet.number);
        this.setState({ diets: currentDiets });
      }
    }).catch(async ex => {
      if (ex && ex.response.data.error.errorKey === keys.DIET_NOT_FOUND_ERROR) {
        const currentDiets = await getOwnDiets(login);
        this.setState({ diets: currentDiets });
      }
    });
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const {
      pageSize,
      currentPage,
      sortColumn,
      diets
    } = this.state;

    return (
        <div className="row mt-5">
          <div className="col mt-5">
            <DietsTable diets={diets}
                        myTable={true}
                        sortColumn={sortColumn}
                        onDelete={this.handleDelete}
                        onUpdate={this.handleUpdate}
                        onApply={this.handleApply}
                        onSort={this.handleSort}/>
          </div>
        </div>
    );
  }
}

export default withTranslation()(MyDietsComponent);