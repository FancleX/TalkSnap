
const InputChecker = {

    /**
     * check input email
     * 
     * @param {user input} input 
     */
    emailChecker(email) {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
            return true;
        }
        return false;
    },

    /**
     * check general input string, cannot be empty and all spaces
     * 
     * @param {user input} input 
     */
    generalChecker(input) {
        return input === null || input.match(/^ *$/) !== null;
    }

}

export default InputChecker;