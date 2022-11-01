import React, { Component } from 'react'
import logo from './mainStreetAuto.svg'
import axios from 'axios'
import './App.css'

// Toast notification dependencies
import { ToastContainer, toast } from 'react-toastify'

class App extends Component {
   title = "None"
  constructor(props) {
    super(props)

    this.state = {
      NationalityToDisplay: [],
      Teams: [],
      Player:[],
      Brands:[],
      Prices:[],
      Websites:[],

    }

    this.getPlayer = this.getPlayer.bind(this)
    this.getBrands = this.getBrands.bind(this)
    this.getTeams = this.getTeams.bind(this)
    this.getPrices = this.getPrices.bind(this)
    this.getWebsites = this.getWebsites.bind(this)

   }


   // get all playser

   getPlayer() {
    axios.get('http://localhost:8090/elitemchi/Player').then(
      data =>{
        console.log(data.data);
        this.setState({Player : 
        data.data})         
        console.log(this.state.Player)

      }
    )
  }

  //get all brands 
  getBrands() {
    this.title = "Brands"
    axios.get('http://localhost:8090/elitemchi/allBrand').then(
      data =>{
        console.log(data.data);
        this.setState({Brands : 
        data.data})         
        console.log(this.state.Brands)
      }
    )
  }

   //get all Websites 
   getWebsites() {
    this.title = "Websites"

    axios.get('http://localhost:8090/elitemchi/allwebsites').then(
      data =>{
        console.log(data.data);
        this.setState({Websites : 
        data.data})         
        console.log(this.state.Websites)

      }
    )
  }

   //get all prices 
   getPrices() {
    this.title = "Prices"

    axios.get('http://localhost:8090/elitemchi/prices').then(
      data =>{
        console.log(data.data);
        this.setState({Prices : 
        data.data})         
        console.log(this.state.Prices)

      }
    )
  }

  

  getTeams() {
    
    axios.get('http://localhost:8090/elitemchi/teams').then(
      data =>{
        console.log(data.data);
        this.setState({Teams : 
        data.data})         
        console.log(this.state.Teams)

      }
    )
  }




// check before render prices , websites , brands 


  render() {
    let comp;

    if(this.title=="Brands")
{
   comp = this.state.Brands.map((brand) => {
    return (
      <div >
        <p>Brand Name:  {brand.Brand}</p>

        <hr className="hr" />
      </div>
    )
  })
}
else if(this.title=="Prices") {
  comp = this.state.Prices.map((price) => {
  return ( 
    <div >
      <p>Currency:  {price.Currency}</p>
      <p>EquipmentName:  {price.EquipmentName}</p>
      <p>MoneyValue:  {price.MoneyValue}</p>

      <hr className="hr" />
    </div>
  )
})}
else if(this.title=="Websites") {
  comp =this.state.Websites.map((website) => {
    return (
      <div >
        <p>Website:  {website.Website}</p>
        <hr className="hr" />
      </div>
    )
  })

}


//brands 
    const brands = this.state.Brands.map((brand) => {
      return (
        <div >
          <p>Brand Name:  {brand.Brand}</p>

          <hr className="hr" />
        </div>
      )
    })

    
//prices 
const prices = this.state.Prices.map((price) => {
  return (
    <div >
      <p>Currency:  {price.Currency}</p>
      <p>EquipmentName:  {price.EquipmentName}</p>
      <p>MoneyValue:  {price.MoneyValue}</p>

      <hr className="hr" />
    </div>
  )
})

// websites
   const websites = this.state.Websites.map((websites) => {
  return (
    <div >
      <p>Website:  {websites.Website}</p>
      <hr className="hr" />
    </div>
  )
})

    const handleAge = (e) =>
    {
      
      console.log(e.target.value)
const id =e.target.value
      axios.get('http://localhost:8090/elitemchi/PlayerParAge?a='+id).then(
        data =>{
          console.log(data.data);
          this.setState({Player : 
          data.data})         
          console.log(this.state.Player)
  
        })
    }

    //player 
    const players = this.state.Player.map((player) => {
     
      return (
        <div >
          <p>Firt Name: {player.FirstName} </p>
          <p>Last name: {player.LastName} </p>
          <p>Age : {player.Age} </p>


        

          <hr className="hr" />
        </div>
      )
    })

    //teams
    const teams = this.state.Teams.map((team) => {
     
      return (
        <div >
          <p>TeamName: {team.TeamName} </p>
          <p>Nationality: {team.Nationality} </p>


        

          <hr className="hr" />
        </div>
      )
    })

    const handleCountries = (e) =>
    {
      const country =e.target.value
      axios.get('http://localhost:8090/elitemchi/teamsParNationality?s='+country).then(
        data =>{
          console.log(data.data);
          this.setState({Teams : 
          data.data})         
          console.log(this.state.Teams)
  
        })
    }
     
    return (
      <div>
        <ToastContainer />

        <header className="header">
          <img src="https://www.parisinfo.com/var/otcp/sites/images/node_43/node_51/joueurs-d'esport-%7C-630x405-%7C-%C2%A9-dr/19307954-1-fre-FR/Joueurs-d'Esport-%7C-630x405-%7C-%C2%A9-DR.jpg" width={500} alt="" />

          <button
            className="header-btn1 btn"
            onClick={() => this.resetData('vehicles')}
          >
            Reset Nationality
          </button>

          <button
            className="header-btn2 btn"
            onClick={() => this.resetData('buyers')}
          >
            Reset Teams
          </button>
        </header>

        <div className="btn-container">
        
          <button className="btn-sp btn" onClick={this.getBrands}>
          Brands
          </button>
          <button className="btn-sp btn" onClick={this.getPrices}>
          Prices
          </button>
          <button className="btn-sp btn" onClick={this.getWebsites}>
          Websites
          </button>
        </div>

        <br />

     
        <main className="main-wrapper">
         
          <section className="info-box">
            <h3>Players</h3>
            <input type="number" placeholder='Search By Age' onChange={handleAge}/>
          
           
            {players}
          </section>

        

          <section className="info-box">
            <h3>Teams</h3>
            <select id="country" name="country" onChange={handleCountries}>
    <option>Search By Country</option>
    <option value="German">Germany</option>
    <option value="America">America</option>
    <option value="Poland">Poland</option>
    
    </select>
            {teams}
          </section>
          <section className="info-box">
            <h3>{this.title}</h3>

          {comp}
           
          </section>
        </main>
      </div>
    )
  }
}

export default App
