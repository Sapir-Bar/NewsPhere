import { renderHook } from '@testing-library/react';
import './Search.css';
import {useRef} from 'react';
function Search({setSearchQ}) {

  const searchBox = useRef(null);

  const search = function(){
    setSearchQ(searchBox.current.value);
  }
  return (
<div className="Search">

  <input ref={searchBox} onKeyUp={search} type="text" placeholder="Search for people, places and things" className="input-field" />
  <button type="submit" className="search-button">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
         <circle cx="10" cy="10" r="8" stroke="black" strokeWidth="2" fill="none" />
         <line x1="15" y1="15" x2="20" y2="20" stroke="black" strokeWidth="2" />
      </svg>
   </button>

</div>
  );
}

export default Search;













