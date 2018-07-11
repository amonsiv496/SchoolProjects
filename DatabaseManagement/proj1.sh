#!/bin/bash

# Display Menu
# Description: show menu option
DisplayMenu () {
	echo "Main Options Menu"
	echo "   (1) Find a Record"
	echo "   (2) Add a new Record"
	echo "   (3) Update a Record"
	echo "   (4) Remove a Record"
	echo "   (5) Display Address Book"
	echo "   (6) Exit"
}
# openAndCopy
# Description: This function checks to make sure that the database exsists and that a local copy has been made
openAndCopy() {
	if [ -f ${masterFile} ]; then
		#statements
		if cp ${masterFile} ./'.'${masterFile}; then
		   return 0
		else
			echo "Local copy could not be created"
			exit 1
		fi
	else
		rm "$localFile"
		touch "$localFile"
	fi
}

# replaceWithUpdate
# Description: This fucntion is to replace the data.txt with the updated .data.txt
replaceWithUpdate() {
	if cp ${localFile} ${masterFile}; then
		return 0
	else
		echo "ERROR - Could not save changes"
		exit 1
	fi
}

# Add function
# Description: Add entries to the address book
Add () {

    # Ask user for contact Name, assign to "name"
    echo -n "Contact's Name: " 
    read usrName

    # Ask user for contact Address, assign to "adress"
    echo -n "Contact's Address: " 
    read usrAddress


    # Ask the user for a phone number, assign to "phone"
    echo -n "Contact's Phone number: "
    read usrPhone

    # Ask the user for a email, assign to "email"
    echo -n "Contact's email: "
    read usrEmail

    printf "$usrName;$usrAddress;$usrPhone;$usrEmail\n" >>$localFile

    replaceWithUpdate
}

# List function
# Description: List entries of the address book, with formatted text
List () {
	counter=1
	while read -r name address phone email
	do
		printf "($counter) "
		printf "Name: %s" $name
		printf "\tAddress: %s" $address
		printf "\tPhone: %s" $phone
		printf "\tEmail: %s\n" $email
		counter=$((counter + 1))
	done < "$localFile"  
}

#Find function
#Description:Finds the line number and prints the line containing any input character entered by the user
Find() {
    #"data.txt" file saved into "record_file" variable
    #prompts user input
    echo -n "Enter record to find: "
    #user input saved in "text" variable
    read text

    while [ "$text" = "*" ]; do
        printf "Please enter a valid text to find: "
	read text
    done

    found=0
    while read -r name address phone email
	do
		lineNum=${name%:*}
		name=${name#*:}
		printf "($lineNum)"
		printf "Name: %s" $name
		printf "\tAddress: %s" $address
		printf "\tPhone: %s" $phone
		printf "\tEmail: %s\n" $email
		found=1
	done < <(grep -in $text $localFile)
	if [ "$found" -ne "1" ]; then
		echo "No record found"
		return 1
	fi
}

#removeRecord
#Description: removes a record by removing the line from the file
removeRecord() {
	if Find; then
		echo -n "Enter a line to remove or 0 if none: "
		read rmLine
		remTempFile=".rem.txt"		
		touch "$remTempFile"
		counter=1
		while read -r name address phone email
		do
			if [ "$counter" -ne "$rmLine" ]; then
				printf "%s$IFS" $name >> "$remTempFile"
				printf "%s$IFS" $address >> "$remTempFile"
				printf "%s$IFS" $phone >> "$remTempFile"
				printf "%s\n" $email >> "$remTempFile"
			fi
			counter=$((counter + 1))
		done < "$localFile" 
		cp "$remTempFile" "$localFile"
		rm "$remTempFile"
		replaceWithUpdate
	else
		return 1
	fi
}

#updateRecord
#Description: removes a record by removing the line from the file
updateRecord() {
	if Find; then
		echo -n "Enter a line to update or 0 if none: "
		read rmLine
		if [ "$rmLine" -eq 0 ]; then
			return 0
		fi
		remTempFile=".rem.txt"		
		touch "$remTempFile"
		counter=1
		while read -r name address phone email
		do
			if [ "$counter" -ne "$rmLine" ]; then
				printf "%s$IFS" $name >> "$remTempFile"
				printf "%s$IFS" $address >> "$remTempFile"
				printf "%s$IFS" $phone >> "$remTempFile"
				printf "%s\n" $email >> "$remTempFile"
			fi
			counter=$((counter + 1))
		done < "$localFile" 
		cp "$remTempFile" "$localFile"
		rm "$remTempFile"
		replaceWithUpdate
		Add
	else
		return 1
	fi

}

#=========================MAIN=============================================
#input form file
masterFile="data.txt"
localFile='.'$masterFile

#set separator to pipe symbol
IFS=';'

openAndCopy

while [ 1 ]; do
	#Display the menu
	DisplayMenu

	#get user's input
	echo -n "Enter your option "
	read choice 	

	#if trees
	if [ "$choice" = '1' ]; then
		##find a record
		Find 
	fi

	if [ "$choice" = '2' ]; then
		##add a record
		Add
	fi

	if [ "$choice" = '3' ]; then
		##update a record
		updateRecord
	fi

	if [ "$choice" = '4' ]; then
		##remove a record
		removeRecord
	fi

	if [ "$choice" = '5' ]; then
		##display database
		List
	fi

	if [ "$choice" = '6' ]; then
	    rm "$localFile"
	    exit 0
	fi
        printf "\n"
        

done

