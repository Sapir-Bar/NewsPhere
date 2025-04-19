import './RegistrationButton.css';
function RegistrationButton({information}) {
    return (
      <div className="RegistrationButton">
                
                <h3>{information.title}</h3>  
                <input type="text"  />        
        
        {information.buttonExplanation}
        
      </div>
    );
  }

  
  export default RegistrationButton;
  