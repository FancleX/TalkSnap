
const FileProcessor = {

    upload(file) {
      let input = file.target;
      let reader = new FileReader();
      reader.onload = () => {
        let dataURL = reader.result;
      };
      reader.readAsArrayBuffer(input.files[0]);
      
    }


}